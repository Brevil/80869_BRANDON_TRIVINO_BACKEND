package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.HistoriaMedicaApi;
import com.uniminuto.clinica.models.AnotacionHistoriaRQ;
import com.uniminuto.clinica.models.AnotacionHistoriaRS;
import com.uniminuto.clinica.models.HistoriaMedicaRQ;
import com.uniminuto.clinica.models.HistoriaMedicaRS;
import com.uniminuto.clinica.service.HistoriaMedicaService;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación REST de {@link HistoriaMedicaApi}. Delega toda la lógica de
 * negocio en {@link HistoriaMedicaService}.
 */
@RestController
public class HistoriaMedicaApiController implements HistoriaMedicaApi {

    /**
     * Servicio de negocio de historia médica y sus anotaciones.
     */
    @Autowired
    private HistoriaMedicaService historiaMedicaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<HistoriaMedicaRS> crearHistoriaMedica(HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException {
        return ResponseEntity.ok(historiaMedicaService.crearHistoriaMedica(historiaMedicaRQ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<HistoriaMedicaRS>> listarHistoriasMedicas(
            LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws BadRequestException {
        return ResponseEntity.ok(
                historiaMedicaService.listarHistoriasMedicas(fechaInicial, fechaFinal));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<HistoriaMedicaRS> actualizarHistoriaMedica(
            Long id, HistoriaMedicaRQ historiaMedicaRQ) throws BadRequestException {
        return ResponseEntity.ok(historiaMedicaService.actualizarHistoriaMedica(id, historiaMedicaRQ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<AnotacionHistoriaRS> crearAnotacionHistoria(
            AnotacionHistoriaRQ anotacionHistoriaRQ) throws BadRequestException {
        return ResponseEntity.ok(historiaMedicaService.crearAnotacionHistoria(anotacionHistoriaRQ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<AnotacionHistoriaRS>> listarAnotacionesHistoria(Long historiaId)
            throws BadRequestException {
        return ResponseEntity.ok(historiaMedicaService.listarAnotacionesHistoria(historiaId));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<AnotacionHistoriaRS> actualizarAnotacionHistoria(
            Long id, AnotacionHistoriaRQ anotacionHistoriaRQ) throws BadRequestException {
        return ResponseEntity.ok(
                historiaMedicaService.actualizarAnotacionHistoria(id, anotacionHistoriaRQ));
    }
}
