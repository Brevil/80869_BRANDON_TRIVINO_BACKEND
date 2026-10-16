package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.AnotacionHistoriaApi;
import com.uniminuto.clinica.entity.AnotacionHistoria;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.AnotacionHistoriaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.service.AnotacionHistoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST de anotaciones; delega toda la lógica en {@link AnotacionHistoriaService}.
 */
@RestController
public class AnotacionHistoriaApiController implements AnotacionHistoriaApi {

    @Autowired
    private AnotacionHistoriaService anotacionHistoriaService;

    @Override
    public ResponseEntity<MiRespuestaRS> crearAnotacionHistoria(AnotacionHistoriaRq anotacionHistoria) throws BadRequestException {
        return ResponseEntity.ok(anotacionHistoriaService.crearAnotacionHistoria(anotacionHistoria));
    }

    @Override
    public ResponseEntity<List<AnotacionHistoria>> listarAnotaciones(Long historiaId, Integer mascotaId)
            throws BadRequestException {
        return ResponseEntity.ok(anotacionHistoriaService.listarAnotaciones(historiaId, mascotaId));
    }
}
