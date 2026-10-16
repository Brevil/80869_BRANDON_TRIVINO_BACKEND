package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.entity.Mascota;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.FormulaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.repository.FormulaMedicaRepository;
import com.uniminuto.clinica.repository.MascotaRepository;
import com.uniminuto.clinica.repository.MedicamentoRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Reglas de negocio de fórmulas médicas: relaciones coherentes con la cita y datos válidos.
 */
@Service
public class FormulaServiceImpl implements FormulaService {

    @Autowired
    private FormulaMedicaRepository formulaMedicaRepository;

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private MascotaRepository mascotaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private CitaService citaService;

    @Override
    public List<FormulaMedica> findAllFormulas() {
        return formulaMedicaRepository.findAllByOrderByFechaCreacionRegistroDesc();
    }

    @Override
    public FormulaMedica buscarFormula(Long id) {
        return formulaMedicaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("La fórmula médica no existe", HttpStatus.NOT_FOUND));
    }

    @Override
    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAllByOrderByNombreAsc();
    }

    @Override
    public MiRespuestaRS crearFormula(FormulaMedicaRq formulaMedicaRq) {
        this.validarFormula(formulaMedicaRq);
        FormulaMedica formula = new FormulaMedica();
        formula.setFechaCreacionRegistro(LocalDateTime.now());
        this.guardarFormula(formula, formulaMedicaRq);
        return this.respuesta("Fórmula médica creada correctamente");
    }

    @Override
    public MiRespuestaRS actualizarFormula(FormulaMedicaRq formulaMedicaRq) {
        this.validarFormula(formulaMedicaRq);
        if (formulaMedicaRq.getId() == null) {
            throw new BadRequestException("El id de la fórmula médica es obligatorio");
        }
        FormulaMedica formula = this.buscarFormula(formulaMedicaRq.getId());
        formula.setFechaActualizacionRegistro(LocalDateTime.now());
        this.guardarFormula(formula, formulaMedicaRq);
        return this.respuesta("Fórmula médica actualizada correctamente");
    }

    /**
     * Verifica que cada relación exista, que la mascota coincida con la de la cita y que la fecha
     * de emisión no sea anterior a la consulta; luego guarda la fórmula.
     *
     * @param formula entidad a guardar (nueva o existente).
     * @param rq      datos validados.
     */
    private void guardarFormula(FormulaMedica formula, FormulaMedicaRq rq) {
        Mascota mascota = mascotaRepository.findById(rq.getMascotaId())
                .orElseThrow(() -> new BadRequestException("La mascota no existe", HttpStatus.NOT_FOUND));
        if (mascota.getCliente() == null) {
            throw new BadRequestException("La mascota no pertenece a un cliente registrado");
        }
        Cita cita = citaService.buscarCita(rq.getCitaId());
        if (!cita.getMascota().getMascotaId().equals(mascota.getMascotaId())) {
            throw new BadRequestException("La cita seleccionada pertenece a otra mascota");
        }
        if (rq.getFechaEmision().isBefore(cita.getFechaHora().toLocalDate())) {
            throw new BadRequestException("La fecha de emisión no puede ser anterior a la fecha de la consulta");
        }
        Medico medico = medicoRepository.findById(rq.getMedicoId())
                .orElseThrow(() -> new BadRequestException("El veterinario no existe", HttpStatus.NOT_FOUND));
        Medicamento medicamento = medicamentoRepository.findById(rq.getMedicamentoId())
                .orElseThrow(() -> new BadRequestException("El medicamento no existe", HttpStatus.NOT_FOUND));

        formula.setCita(cita);
        formula.setMascota(mascota);
        formula.setMedico(medico);
        formula.setMedicamento(medicamento);
        formula.setFechaEmision(rq.getFechaEmision());
        formula.setDosis(rq.getDosis());
        formula.setFrecuencia(rq.getFrecuencia());
        formula.setDuracionDias(rq.getDuracionDias());
        formula.setIndicaciones(rq.getIndicaciones());
        formulaMedicaRepository.save(formula);
    }

    /**
     * Valida los campos obligatorios de la fórmula.
     *
     * @param rq solicitud recibida.
     */
    private void validarFormula(FormulaMedicaRq rq) {
        if (rq == null) {
            throw new BadRequestException("La fórmula médica no puede ser nula");
        }
        if (rq.getCitaId() == null) {
            throw new BadRequestException("Debe seleccionar la cita o consulta");
        }
        if (rq.getMascotaId() == null) {
            throw new BadRequestException("Debe seleccionar la mascota");
        }
        if (rq.getMedicoId() == null) {
            throw new BadRequestException("Debe seleccionar el veterinario");
        }
        if (rq.getMedicamentoId() == null) {
            throw new BadRequestException("Debe seleccionar el medicamento");
        }
        if (rq.getFechaEmision() == null) {
            throw new BadRequestException("La fecha de emisión es obligatoria");
        }
        if (rq.getDosis() == null || rq.getDosis().isBlank()) {
            throw new BadRequestException("La dosis es obligatoria");
        }
        if (rq.getFrecuencia() == null || rq.getFrecuencia().isBlank()) {
            throw new BadRequestException("La frecuencia es obligatoria");
        }
        if (rq.getDuracionDias() == null || rq.getDuracionDias() <= 0) {
            throw new BadRequestException("La duración del tratamiento debe ser de al menos un día");
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
