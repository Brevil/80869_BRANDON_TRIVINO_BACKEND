package com.uniminuto.clinica.api;

import com.uniminuto.clinica.models.CitaRQ;
import com.uniminuto.clinica.models.CitaRS;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Contrato REST de los servicios de citas: filtrado por rango de fechas y
 * CRUD (crear/actualizar).
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clinica/citas")
public interface CitaApi {

    /**
     * Filtra las citas del sistema entre una fecha inicial y una fecha
     * final, ordenadas de la más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango (inclusive).
     * @param fechaFinal   límite superior del rango (inclusive).
     * @return listado de citas dentro del rango solicitado.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @GetMapping(value = "", produces = {"application/json"})
    ResponseEntity<List<CitaRS>> filtrarCitasPorFecha(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFinal)
            throws BadRequestException;

    /**
     * Crea una nueva cita en el sistema.
     *
     * @param citaRQ datos de la cita a crear.
     * @return la cita creada, con su identificador asignado.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @PostMapping(value = "", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<CitaRS> crearCita(@RequestBody CitaRQ citaRQ)
            throws BadRequestException;

    /**
     * Actualiza una cita ya existente en el sistema.
     *
     * @param id     identificador de la cita a actualizar.
     * @param citaRQ nuevos datos de la cita.
     * @return la cita actualizada.
     * @throws BadRequestException si la cita no existe o la petición es inválida.
     */
    @PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<CitaRS> actualizarCita(@PathVariable Long id, @RequestBody CitaRQ citaRQ)
            throws BadRequestException;
}
