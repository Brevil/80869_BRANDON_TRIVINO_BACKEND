package com.uniminuto.clinica.api;

import com.uniminuto.clinica.models.MedicoRS;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrato REST del servicio de listado de médicos con su especialización.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clinica/medicos")
public interface MedicoApi {

    /**
     * Lista todos los médicos registrados junto con su especialización.
     *
     * @return listado de médicos con su especialización.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @GetMapping(value = "", produces = {"application/json"})
    ResponseEntity<List<MedicoRS>> listarMedicosConEspecializacion()
            throws BadRequestException;
}
