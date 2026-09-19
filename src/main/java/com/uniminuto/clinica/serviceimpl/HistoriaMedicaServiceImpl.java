package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.models.AnotacionHistoriaRQ;
import com.uniminuto.clinica.models.AnotacionHistoriaRS;
import com.uniminuto.clinica.models.HistoriaMedicaRQ;
import com.uniminuto.clinica.models.HistoriaMedicaRS;
import com.uniminuto.clinica.repository.AnotacionHistoriaRepository;
import com.uniminuto.clinica.repository.HistoriaMedicaRepository;
import com.uniminuto.clinica.service.HistoriaMedicaService;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link HistoriaMedicaService}.
 */
@Service
public class HistoriaMedicaServiceImpl implements HistoriaMedicaService {

    /**
     * Repositorio JPA de historias médicas.
     */
    @Autowired
    private HistoriaMedicaRepository historiaMedicaRepository;

    /**
     * Repositorio JPA de anotaciones de historia médica.
     */
    @Autowired
    private AnotacionHistoriaRepository anotacionHistoriaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoriaMedicaRS crearHistoriaMedica(HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException {
        if (historiaMedicaRQ == null || historiaMedicaRQ.getPacienteId() == null) {
            throw new BadRequestException("pacienteId es obligatorio");
        }
        HistoriaMedica historia = new HistoriaMedica();
        historia.setPacienteId(historiaMedicaRQ.getPacienteId());
        historia.setFechaCreacion(LocalDateTime.now());
        return aHistoriaMedicaRS(historiaMedicaRepository.save(historia));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<HistoriaMedicaRS> listarHistoriasMedicas(
            LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws BadRequestException {
        if (fechaInicial == null || fechaFinal == null) {
            throw new BadRequestException("Las fechas inicial y final son obligatorias");
        }
        if (fechaInicial.isAfter(fechaFinal)) {
            throw new BadRequestException("La fecha inicial no puede ser posterior a la fecha final");
        }
        List<HistoriaMedica> historias = historiaMedicaRepository
                .findByFechaCreacionBetweenOrderByFechaCreacionDesc(fechaInicial, fechaFinal);
        return historias.stream().map(this::aHistoriaMedicaRS).toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HistoriaMedicaRS actualizarHistoriaMedica(Long id, HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException {
        if (historiaMedicaRQ == null || historiaMedicaRQ.getPacienteId() == null) {
            throw new BadRequestException("pacienteId es obligatorio");
        }
        HistoriaMedica historia = historiaMedicaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe una historia médica con id " + id));
        historia.setPacienteId(historiaMedicaRQ.getPacienteId());
        return aHistoriaMedicaRS(historiaMedicaRepository.save(historia));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnotacionHistoriaRS crearAnotacionHistoria(AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException {
        if (anotacionHistoriaRQ == null || anotacionHistoriaRQ.getHistoriaId() == null
                || anotacionHistoriaRQ.getMedicoId() == null
                || anotacionHistoriaRQ.getDescripcion() == null
                || anotacionHistoriaRQ.getDescripcion().isBlank()) {
            throw new BadRequestException("historiaId, medicoId y descripcion son obligatorios");
        }
        HistoriaMedica historia = historiaMedicaRepository.findById(anotacionHistoriaRQ.getHistoriaId())
                .orElseThrow(() -> new BadRequestException(
                        "No existe una historia médica con id " + anotacionHistoriaRQ.getHistoriaId()));

        AnotacionHistoria anotacion = new AnotacionHistoria();
        anotacion.setHistoria(historia);
        anotacion.setMedicoId(anotacionHistoriaRQ.getMedicoId());
        anotacion.setDescripcion(anotacionHistoriaRQ.getDescripcion());
        anotacion.setFecha(LocalDateTime.now());
        return aAnotacionHistoriaRS(anotacionHistoriaRepository.save(anotacion));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<AnotacionHistoriaRS> listarAnotacionesHistoria(Long historiaId)
            throws BadRequestException {
        if (!historiaMedicaRepository.existsById(historiaId)) {
            throw new BadRequestException("No existe una historia médica con id " + historiaId);
        }
        return anotacionHistoriaRepository.findByHistoria_IdOrderByFechaDesc(historiaId).stream()
                .map(this::aAnotacionHistoriaRS)
                .toList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AnotacionHistoriaRS actualizarAnotacionHistoria(Long id, AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException {
        if (anotacionHistoriaRQ == null || anotacionHistoriaRQ.getDescripcion() == null
                || anotacionHistoriaRQ.getDescripcion().isBlank()) {
            throw new BadRequestException("descripcion es obligatoria");
        }
        AnotacionHistoria anotacion = anotacionHistoriaRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("No existe una anotación con id " + id));
        anotacion.setDescripcion(anotacionHistoriaRQ.getDescripcion());
        if (anotacionHistoriaRQ.getMedicoId() != null) {
            anotacion.setMedicoId(anotacionHistoriaRQ.getMedicoId());
        }
        return aAnotacionHistoriaRS(anotacionHistoriaRepository.save(anotacion));
    }

    /**
     * Convierte una entidad {@link HistoriaMedica} en su DTO de respuesta.
     *
     * @param historia entidad leída o guardada en la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private HistoriaMedicaRS aHistoriaMedicaRS(HistoriaMedica historia) {
        HistoriaMedicaRS rs = new HistoriaMedicaRS();
        rs.setId(historia.getId());
        rs.setPacienteId(historia.getPacienteId());
        rs.setFechaCreacion(historia.getFechaCreacion());
        return rs;
    }

    /**
     * Convierte una entidad {@link AnotacionHistoria} en su DTO de respuesta.
     *
     * @param anotacion entidad leída o guardada en la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private AnotacionHistoriaRS aAnotacionHistoriaRS(AnotacionHistoria anotacion) {
        AnotacionHistoriaRS rs = new AnotacionHistoriaRS();
        rs.setId(anotacion.getId());
        rs.setHistoriaId(anotacion.getHistoria().getId());
        rs.setMedicoId(anotacion.getMedicoId());
        rs.setFecha(anotacion.getFecha());
        rs.setDescripcion(anotacion.getDescripcion());
        return rs;
    }
}
