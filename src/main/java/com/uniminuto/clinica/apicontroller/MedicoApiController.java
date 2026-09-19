package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.MedicoApi;
import com.uniminuto.clinica.models.MedicoRS;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementación REST de {@link MedicoApi}. Delega toda la lógica de negocio
 * en {@link MedicoService}.
 */
@RestController
public class MedicoApiController implements MedicoApi {

    /**
     * Servicio de negocio de médicos.
     */
    @Autowired
    private MedicoService medicoService;

    /**
     * {@inheritDoc}
     */
    @Override
    public ResponseEntity<List<MedicoRS>> listarMedicosConEspecializacion()
            throws BadRequestException {
        return ResponseEntity.ok(medicoService.listarMedicosConEspecializacion());
    }
}
