package com.uniminuto.clinica.api;

import com.uniminuto.clinica.models.FormulaMedicaRS;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Contrato REST de los servicios relacionados con el inventario de fórmulas
 * médicas.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clinica/formulas-medicas")
public interface FormulaMedicaApi {

    /**
     * Lista todas las fórmulas médicas del inventario, ordenadas por fecha
     * de creación de la más reciente a la más antigua.
     *
     * @return listado de fórmulas médicas.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @GetMapping(value = "", produces = {"application/json"})
    ResponseEntity<List<FormulaMedicaRS>> listarFormulasMedicas()
            throws BadRequestException;
}
