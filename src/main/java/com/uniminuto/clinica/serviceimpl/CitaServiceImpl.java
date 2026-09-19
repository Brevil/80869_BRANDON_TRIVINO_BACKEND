package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.models.CitaRQ;
import com.uniminuto.clinica.models.CitaRS;
import com.uniminuto.clinica.repository.CitaRepository;
import com.uniminuto.clinica.service.CitaService;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link CitaService}.
 */
@Service
public class CitaServiceImpl implements CitaService {

    /**
     * Repositorio JPA de citas.
     */
    @Autowired
    private CitaRepository citaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<CitaRS> filtrarCitasPorFecha(LocalDateTime fechaInicial, LocalDateTime fechaFinal)
            throws BadRequestException {
        if (fechaInicial == null || fechaFinal == null) {
            throw new BadRequestException("Las fechas inicial y final son obligatorias");
        }
        if (fechaInicial.isAfter(fechaFinal)) {
            throw new BadRequestException("La fecha inicial no puede ser posterior a la fecha final");
        }
        List<Cita> citas =
                citaRepository.findByFechaHoraBetweenOrderByFechaHoraDesc(fechaInicial, fechaFinal);
        return citas.stream().map(this::aCitaRS).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CitaRS crearCita(CitaRQ citaRQ) throws BadRequestException {
        validarCitaRQ(citaRQ);
        Cita cita = new Cita();
        copiarDatos(citaRQ, cita);
        Cita citaGuardada = citaRepository.save(cita);
        return aCitaRS(citaGuardada);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CitaRS actualizarCita(Long id, CitaRQ citaRQ) throws BadRequestException {
        validarCitaRQ(citaRQ);
        Cita cita = citaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe una cita con id " + id));
        copiarDatos(citaRQ, cita);
        Cita citaActualizada = citaRepository.save(cita);
        return aCitaRS(citaActualizada);
    }

    /**
     * Valida que los datos obligatorios de la petición de cita estén
     * presentes antes de crear o actualizar.
     *
     * @param citaRQ datos de la cita a validar.
     * @throws BadRequestException si falta algún dato obligatorio.
     */
    private void validarCitaRQ(CitaRQ citaRQ) throws BadRequestException {
        if (citaRQ == null) {
            throw new BadRequestException("El cuerpo de la petición es obligatorio");
        }
        if (citaRQ.getClienteId() == null || citaRQ.getMascotaId() == null
                || citaRQ.getMedicoId() == null || citaRQ.getFechaHora() == null
                || citaRQ.getEstado() == null || citaRQ.getEstado().isBlank()) {
            throw new BadRequestException(
                    "clienteId, mascotaId, medicoId, fechaHora y estado son obligatorios");
        }
    }

    /**
     * Copia los datos del DTO de petición sobre la entidad de cita.
     *
     * @param citaRQ datos de origen.
     * @param cita   entidad de destino.
     */
    private void copiarDatos(CitaRQ citaRQ, Cita cita) {
        cita.setClienteId(citaRQ.getClienteId());
        cita.setMascotaId(citaRQ.getMascotaId());
        cita.setMedicoId(citaRQ.getMedicoId());
        cita.setFechaHora(citaRQ.getFechaHora());
        cita.setEstado(citaRQ.getEstado());
        cita.setMotivo(citaRQ.getMotivo());
    }

    /**
     * Convierte una entidad {@link Cita} en su DTO de respuesta {@link CitaRS}.
     *
     * @param cita entidad leída o guardada en la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private CitaRS aCitaRS(Cita cita) {
        CitaRS rs = new CitaRS();
        rs.setId(cita.getId());
        rs.setClienteId(cita.getClienteId());
        rs.setMascotaId(cita.getMascotaId());
        rs.setMedicoId(cita.getMedicoId());
        rs.setFechaHora(cita.getFechaHora());
        rs.setEstado(cita.getEstado());
        rs.setMotivo(cita.getMotivo());
        return rs;
    }
}
