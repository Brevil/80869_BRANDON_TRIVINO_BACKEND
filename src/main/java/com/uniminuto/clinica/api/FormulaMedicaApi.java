package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.FormulaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrato REST de fórmulas médicas.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/formula-medica")
public interface FormulaMedicaApi {

    /**
     * Lista las fórmulas, de la más reciente a la más antigua.
     *
     * @return lista de fórmulas.
     * @throws BadRequestException excepción de negocio.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<FormulaMedica>> listarFormulas()
            throws BadRequestException;

    /**
     * Consulta una fórmula por id.
     *
     * @param id id de la fórmula.
     * @return la fórmula solicitada.
     * @throws BadRequestException si no existe (404).
     */
    @GetMapping(value = "/{id}",
            produces = {"application/json"})
    ResponseEntity<FormulaMedica> buscarFormula(@PathVariable Long id)
            throws BadRequestException;

    /**
     * Lista los medicamentos disponibles para prescribir.
     *
     * @return lista de medicamentos.
     */
    @GetMapping(value = "/medicamentos",
            produces = {"application/json"})
    ResponseEntity<List<Medicamento>> listarMedicamentos();

    /**
     * Crea una fórmula médica.
     *
     * @param formula datos de la fórmula.
     * @return respuesta con el resultado.
     * @throws BadRequestException si no cumple las reglas de negocio.
     */
    @PostMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> crearFormula(@RequestBody FormulaMedicaRq formula)
            throws BadRequestException;

    /**
     * Edita una fórmula médica existente.
     *
     * @param formula datos de la fórmula, incluido su id.
     * @return respuesta con el resultado.
     * @throws BadRequestException si no cumple las reglas de negocio.
     */
    @PutMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> actualizarFormula(@RequestBody FormulaMedicaRq formula)
            throws BadRequestException;
}
