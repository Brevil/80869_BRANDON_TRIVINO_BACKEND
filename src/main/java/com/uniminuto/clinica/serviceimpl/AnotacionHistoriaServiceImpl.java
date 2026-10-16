package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.AnotacionHistoriaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.repository.AnotacionHistoriaRepository;
import com.uniminuto.clinica.repository.HistoriaMedicaRepository;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.AnotacionHistoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Reglas de negocio de anotaciones: solo se agregan a historias existentes y con datos completos.
 */
@Service
public class AnotacionHistoriaServiceImpl implements AnotacionHistoriaService {

    @Autowired
    private HistoriaMedicaRepository historiaMedicaRepository;

    @Autowired
    private AnotacionHistoriaRepository anotacionHistoriaRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public MiRespuestaRS crearAnotacionHistoria(AnotacionHistoriaRq anotacionHistoriaRq) {
        this.validarAnotacionHistoria(anotacionHistoriaRq);
        HistoriaMedica historia = historiaMedicaRepository.findById(anotacionHistoriaRq.getHistoriaId())
                .orElseThrow(() -> new BadRequestException("La historia clínica no existe", HttpStatus.NOT_FOUND));
        Medico medico = medicoRepository.findById(anotacionHistoriaRq.getMedicoId())
                .orElseThrow(() -> new BadRequestException("El veterinario no existe", HttpStatus.NOT_FOUND));

        AnotacionHistoria anotacion = new AnotacionHistoria();
        anotacion.setHistoria(historia);
        anotacion.setMedico(medico);
        anotacion.setFecha(LocalDateTime.now());
        anotacion.setDescripcion(anotacionHistoriaRq.getDescripcion());
        anotacion.setObservacion(anotacionHistoriaRq.getObservacion());
        anotacion.setDiagnostico(anotacionHistoriaRq.getDiagnostico());
        anotacionHistoriaRepository.save(anotacion);

        MiRespuestaRS rta = new MiRespuestaRS();
        rta.setStatus(200);
        rta.setMessage("Anotación agregada a la historia clínica correctamente");
        return rta;
    }

    @Override
    public List<AnotacionHistoria> listarAnotaciones(Long historiaId, Integer mascotaId) {
        if (historiaId != null) {
            return anotacionHistoriaRepository.findByHistoriaIdOrderByFechaDesc(historiaId);
        }
        if (mascotaId != null) {
            return anotacionHistoriaRepository.findByHistoriaMascotaMascotaIdOrderByFechaDesc(mascotaId);
        }
        throw new BadRequestException("Debe indicar la historia clínica o la mascota");
    }

    /**
     * Valida los campos obligatorios de la anotación.
     *
     * @param rq solicitud recibida.
     */
    private void validarAnotacionHistoria(AnotacionHistoriaRq rq) {
        if (rq == null) {
            throw new BadRequestException("La anotación de historia clínica es obligatoria");
        }
        if (rq.getHistoriaId() == null) {
            throw new BadRequestException("Debe indicar la historia clínica");
        }
        if (rq.getMedicoId() == null) {
            throw new BadRequestException("Debe seleccionar el veterinario");
        }
        if (rq.getDescripcion() == null || rq.getDescripcion().isBlank()) {
            throw new BadRequestException("La descripción de la anotación es obligatoria");
        }
        if (rq.getDiagnostico() == null || rq.getDiagnostico().isBlank()) {
            throw new BadRequestException("El diagnóstico o evolución es obligatorio");
        }
    }
}
