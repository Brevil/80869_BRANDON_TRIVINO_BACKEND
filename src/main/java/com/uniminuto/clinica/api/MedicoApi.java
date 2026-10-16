package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Medico;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * Contrato REST de veterinarios.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/medico")
public interface MedicoApi {

    /**
     * Lista los veterinarios ordenados por nombre.
     *
     * @return lista de veterinarios.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<Medico>> listarMedicos();
}
