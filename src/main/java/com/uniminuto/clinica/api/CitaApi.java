package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.CitaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Contrato REST de citas médicas.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cita")
public interface CitaApi {

    /**
     * Lista todas las citas, de la más reciente a la más antigua.
     *
     * @return lista de citas.
     * @throws BadRequestException excepción de negocio.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<Cita>> listarCitas()
            throws BadRequestException;

    /**
     * Lista las citas dentro de un rango de fechas.
     *
     * @param fechaInicio inicio del rango.
     * @param fechaFin    fin del rango.
     * @return citas del rango.
     * @throws BadRequestException si el rango es inválido.
     */
    @GetMapping(value = "/listar-rango",
            produces = {"application/json"})
    ResponseEntity<List<Cita>> listarCitasRango(
            @RequestParam LocalDateTime fechaInicio,
            @RequestParam LocalDateTime fechaFin
    )
            throws BadRequestException;

    /**
     * Consulta una cita por id.
     *
     * @param id id de la cita.
     * @return la cita solicitada.
     * @throws BadRequestException si no existe (404).
     */
    @GetMapping(value = "/{id}",
            produces = {"application/json"})
    ResponseEntity<Cita> buscarCita(@PathVariable Long id)
            throws BadRequestException;

    /**
     * Agenda una nueva cita.
     *
     * @param cita datos de la cita.
     * @return respuesta con el resultado.
     * @throws BadRequestException si no cumple las reglas de negocio.
     */
    @PostMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> crearCita(
            @RequestBody CitaRq cita
    )
            throws BadRequestException;

    /**
     * Edita una cita existente.
     *
     * @param cita datos de la cita, incluido su id.
     * @return respuesta con el resultado.
     * @throws BadRequestException si la cita no existe o no puede modificarse.
     */
    @PutMapping(value = "/actualizar",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> actualizarCita(
            @RequestBody CitaRq cita
    )
            throws BadRequestException;
}
