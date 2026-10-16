package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Cliente;
import com.uniminuto.clinica.entity.Mascota;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.CitaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.repository.ClienteRepository;
import com.uniminuto.clinica.repository.MascotaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * Reglas de negocio de citas: existencia de relaciones, cruces de horario, fechas y estados.
 */
@Service
public class CitaServiceImpl implements CitaService {

    /** Minutos antes y después de una cita en los que no se puede agendar otra. */
    private static final int MARGEN_MINUTOS = 15;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Cita> listarCitas() {
        return citaRepository.findAllByOrderByFechaHoraDesc();
    }

    @Override
    public Cita buscarCita(Long id) {
        return citaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("La cita no existe", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Cita> listarCitasRango(LocalDateTime fechaInicio, LocalDateTime fechaFin)
            throws BadRequestException {
        this.validarFechas(fechaInicio, fechaFin);
        List<Cita> citas = citaRepository.findByFechaHoraBetween(fechaInicio, fechaFin);
        citas.sort((cita1, cita2)
                -> cita1.getFechaHora().compareTo(cita2.getFechaHora()));
        return citas;
    }

    private void validarFechas(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws BadRequestException {
        if (fechaInicio == null || fechaFin == null) {
            throw new BadRequestException("Las fechas no pueden ser nulas");
        }
        if (fechaInicio.isAfter(fechaFin)) {
            throw new BadRequestException("La fecha de inicio no puede ser mayor a la fecha de fin");
        }
    }

    @Override
    public MiRespuestaRS crearCita(CitaRq citaRq) throws BadRequestException {
        this.validarCita(citaRq);
        Cita cita = new Cita();
        this.guardarCita(cita, citaRq);
        return this.respuesta("Cita agendada correctamente");
    }

    @Override
    public MiRespuestaRS actualizarCita(CitaRq citaRq) throws BadRequestException {
        this.validarCita(citaRq);
        if (citaRq.getId() == null) {
            throw new BadRequestException("El id de la cita es obligatorio");
        }
        Cita cita = this.buscarCita(citaRq.getId());
        if (Cita.ESTADO_ATENDIDA.equals(cita.getEstado())) {
            throw new BadRequestException("La cita ya fue atendida y su información no puede modificarse");
        }
        // Una cancelada solo cambia si se reactiva de forma explícita (nuevo estado distinto de CANCELADA).
        if (Cita.ESTADO_CANCELADA.equals(cita.getEstado()) && Cita.ESTADO_CANCELADA.equals(citaRq.getEstado())) {
            throw new BadRequestException("La cita está cancelada y no puede modificarse. "
                    + "Para reactivarla, cambie su estado a uno activo.");
        }
        this.guardarCita(cita, citaRq);
        return this.respuesta("Cita actualizada correctamente");
    }

    /**
     * Valida relaciones y horarios, y guarda la cita con los datos recibidos.
     *
     * @param cita   entidad a guardar (nueva o existente).
     * @param citaRq datos validados.
     */
    private void guardarCita(Cita cita, CitaRq citaRq) {
        Cliente cliente = clienteRepository.findById(citaRq.getClienteId())
                .orElseThrow(() -> new BadRequestException("El cliente no existe", HttpStatus.NOT_FOUND));
        Mascota mascota = mascotaRepository.findById(citaRq.getMascotaId())
                .orElseThrow(() -> new BadRequestException("La mascota no existe", HttpStatus.NOT_FOUND));
        if (mascota.getCliente() == null || !mascota.getCliente().getId().equals(cliente.getId())) {
            throw new BadRequestException("La mascota no pertenece al cliente seleccionado");
        }
        Medico medico = medicoRepository.findById(citaRq.getMedicoId())
                .orElseThrow(() -> new BadRequestException("El veterinario no existe", HttpStatus.NOT_FOUND));
        if (!Cita.ESTADOS.contains(citaRq.getEstado())) {
            throw new BadRequestException("El estado de la cita no es válido");
        }
        // Cancelar libera el horario, por eso solo las citas activas se validan contra otras.
        if (!Cita.ESTADO_CANCELADA.equals(citaRq.getEstado())) {
            this.validarHorario(cita.getId(), citaRq.getFechaHora(), mascota, medico);
        }

        cita.setCliente(cliente);
        cita.setMascota(mascota);
        cita.setMedico(medico);
        cita.setFechaHora(citaRq.getFechaHora());
        cita.setMotivo(citaRq.getMotivo());
        cita.setEstado(citaRq.getEstado());
        citaRepository.save(cita);
    }

    /**
     * Verifica que la fecha no sea pasada y que ni el veterinario ni la mascota tengan otra cita
     * dentro del margen de {@link #MARGEN_MINUTOS} minutos.
     *
     * @param citaId     id de la cita que se edita, o null si es nueva (se ignora en la comparación).
     * @param fechaHora  fecha y hora solicitada.
     * @param mascota    mascota de la cita.
     * @param medico     veterinario de la cita.
     */
    private void validarHorario(Long citaId, LocalDateTime fechaHora, Mascota mascota, Medico medico) {
        if (fechaHora.toLocalDate().isBefore(LocalDate.now())) {
            throw new BadRequestException("No se pueden registrar citas en fechas anteriores a la fecha actual");
        }
        LocalDateTime desde = fechaHora.minusMinutes(MARGEN_MINUTOS);
        LocalDateTime hasta = fechaHora.plusMinutes(MARGEN_MINUTOS);

        boolean medicoOcupado = citaRepository.findByMedicoAndFechaHoraBetween(medico, desde, hasta)
                .stream().anyMatch(otra -> this.esConflicto(otra, citaId));
        if (medicoOcupado) {
            throw new BadRequestException("El veterinario ya tiene una cita en ese horario. "
                    + "Debe haber al menos 15 minutos entre citas.");
        }

        boolean mascotaOcupada = citaRepository.findByMascotaAndFechaHoraBetween(mascota, desde, hasta)
                .stream().anyMatch(otra -> this.esConflicto(otra, citaId));
        if (mascotaOcupada) {
            throw new BadRequestException("La mascota ya tiene una cita en ese horario");
        }
    }

    /**
     * Indica si otra cita bloquea el horario: debe ser distinta a la que se edita y no estar cancelada.
     *
     * @param otra   cita encontrada en el rango.
     * @param citaId id de la cita que se edita, o null.
     * @return true si la otra cita genera conflicto.
     */
    private boolean esConflicto(Cita otra, Long citaId) {
        return !Objects.equals(otra.getId(), citaId) && !Cita.ESTADO_CANCELADA.equals(otra.getEstado());
    }

    /**
     * Valida los campos obligatorios de la solicitud de cita.
     *
     * @param citaRq solicitud recibida.
     */
    private void validarCita(CitaRq citaRq) {
        if (citaRq == null) {
            throw new BadRequestException("La cita no puede ser nula");
        }
        if (citaRq.getClienteId() == null) {
            throw new BadRequestException("Debe seleccionar el cliente");
        }
        if (citaRq.getMascotaId() == null) {
            throw new BadRequestException("Debe seleccionar la mascota");
        }
        if (citaRq.getMedicoId() == null) {
            throw new BadRequestException("Debe seleccionar el veterinario");
        }
        if (citaRq.getFechaHora() == null) {
            throw new BadRequestException("La fecha y hora de la cita son obligatorias");
        }
        if (citaRq.getMotivo() == null || citaRq.getMotivo().isBlank()) {
            throw new BadRequestException("El motivo de la consulta es obligatorio");
        }
        if (citaRq.getEstado() == null) {
            throw new BadRequestException("El estado de la cita es obligatorio");
        }
    }

    /**
     * @param mensaje texto para el usuario.
     * @return respuesta de éxito con el mensaje indicado.
     */
    private MiRespuestaRS respuesta(String mensaje) {
        MiRespuestaRS rta = new MiRespuestaRS();
        rta.setStatus(200);
        rta.setMessage(mensaje);
        return rta;
    }
}
