package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.HistoriaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.repository.HistoriaMedicaRepository;
import com.uniminuto.clinica.service.CitaService;
import com.uniminuto.clinica.service.HistoriaMedicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Reglas de negocio de historias clínicas: solo se generan desde una cita atendida, una por cita.
 */
@Service
public class HistoriaMedicaServiceImpl implements HistoriaMedicaService {

    @Autowired
    private HistoriaMedicaRepository historiaMedicaRepository;

    @Autowired
    private CitaService citaService;

    @Override
    public List<HistoriaMedica> listarHistorias() {
        return historiaMedicaRepository.findAllByOrderByFechaHoraDesc();
    }

    @Override
    public MiRespuestaRS crearHistoria(HistoriaMedicaRq rq) {
        if (rq == null || rq.getCitaId() == null) {
            throw new BadRequestException("Debe seleccionar la cita");
        }
        if (rq.getDiagnostico() == null || rq.getDiagnostico().isBlank()) {
            throw new BadRequestException("El diagnóstico es obligatorio");
        }
        Cita cita = citaService.buscarCita(rq.getCitaId());
        if (!Cita.ESTADO_ATENDIDA.equals(cita.getEstado())) {
            throw new BadRequestException("Solo se puede crear una historia clínica para una cita atendida");
        }
        if (historiaMedicaRepository.findByCita(cita).isPresent()) {
            throw new BadRequestException("La cita ya tiene una historia clínica");
        }

        // Cliente, mascota, veterinario, fecha y motivo se toman de la cita para mantener la coherencia.
        HistoriaMedica historia = new HistoriaMedica();
        historia.setCita(cita);
        historia.setMascota(cita.getMascota());
        historia.setMedico(cita.getMedico());
        historia.setFechaHora(cita.getFechaHora());
        historia.setMotivo(cita.getMotivo());
        historia.setDiagnostico(rq.getDiagnostico());
        historia.setObservaciones(rq.getObservaciones());
        historiaMedicaRepository.save(historia);

        MiRespuestaRS rta = new MiRespuestaRS();
        rta.setStatus(200);
        rta.setMessage("Historia clínica creada correctamente");
        return rta;
    }
}
