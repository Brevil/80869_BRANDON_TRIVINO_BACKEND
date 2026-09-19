package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.FormulaMedicaApi;
import com.uniminuto.clinica.models.FormulaMedicaRS;
import com.uniminuto.clinica.service.FormulaMedicaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación REST de {@link FormulaMedicaApi}. Delega toda la lógica de
 * negocio en {@link FormulaMedicaService}.
 */
@RestController
public class FormulaMedicaApiController implements FormulaMedicaApi {

    /**
     * Servicio de negocio de fórmulas médicas.
     */
    @Autowired
    private FormulaMedicaService formulaMedicaService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<FormulaMedicaRS>> listarFormulasMedicas()
            throws BadRequestException {
        return ResponseEntity.ok(formulaMedicaService.listarFormulasMedicas());
    }
}
