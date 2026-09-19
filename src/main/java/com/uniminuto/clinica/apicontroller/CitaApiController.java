package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.CitaApi;
import com.uniminuto.clinica.models.CitaRQ;
import com.uniminuto.clinica.models.CitaRS;
import com.uniminuto.clinica.service.CitaService;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación REST de {@link CitaApi}. Delega toda la lógica de negocio
 * en {@link CitaService}.
 */
@RestController
public class CitaApiController implements CitaApi {

    /**
     * Servicio de negocio de citas.
     */
    @Autowired
    private CitaService citaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<CitaRS>> filtrarCitasPorFecha(
            LocalDateTime fechaInicial, LocalDateTime fechaFinal) throws BadRequestException {
        return ResponseEntity.ok(citaService.filtrarCitasPorFecha(fechaInicial, fechaFinal));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CitaRS> crearCita(CitaRQ citaRQ) throws BadRequestException {
        return ResponseEntity.ok(citaService.crearCita(citaRQ));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<CitaRS> actualizarCita(Long id, CitaRQ citaRQ)
            throws BadRequestException {
        return ResponseEntity.ok(citaService.actualizarCita(id, citaRQ));
    }
}
