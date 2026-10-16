package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.HistoriaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrato REST de historias clínicas.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/historia-medica")
public interface HistoriaMedicaApi {

    /**
     * Lista las historias clínicas, de la más reciente a la más antigua.
     *
     * @return lista de historias.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<HistoriaMedica>> listarHistorias();

    /**
     * Crea la historia clínica de una cita atendida.
     *
     * @param historiaMedica id de la cita, diagnóstico y observaciones.
     * @return respuesta con el resultado.
     * @throws BadRequestException si no cumple las reglas de negocio.
     */
    @PostMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> crearHistoria(
            @RequestBody HistoriaMedicaRq historiaMedica
    )
            throws BadRequestException;
}
