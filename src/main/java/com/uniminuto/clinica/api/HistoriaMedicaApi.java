package com.uniminuto.clinica.api;

import com.uniminuto.clinica.models.AnotacionHistoriaRQ;
import com.uniminuto.clinica.models.AnotacionHistoriaRS;
import com.uniminuto.clinica.models.HistoriaMedicaRQ;
import com.uniminuto.clinica.models.HistoriaMedicaRS;
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
 * Contrato REST del CRUD de historia médica y de los servicios de creación,
 * listado y actualización de anotaciones de historia.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/clinica/historias-medicas")
public interface HistoriaMedicaApi {

    /**
     * Crea una nueva historia médica.
     *
     * @param historiaMedicaRQ datos de la historia médica a crear.
     * @return la historia médica creada, con su identificador asignado.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @PostMapping(value = "", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<HistoriaMedicaRS> crearHistoriaMedica(@RequestBody HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException;

    /**
     * Lista las historias médicas creadas entre una fecha inicial y una
     * fecha final, ordenadas de la más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango (inclusive).
     * @param fechaFinal   límite superior del rango (inclusive).
     * @return listado de historias médicas dentro del rango solicitado.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @GetMapping(value = "", produces = {"application/json"})
    ResponseEntity<List<HistoriaMedicaRS>> listarHistoriasMedicas(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicial,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFinal)
            throws BadRequestException;

    /**
     * Actualiza una historia médica ya existente.
     *
     * @param id               identificador de la historia médica a actualizar.
     * @param historiaMedicaRQ nuevos datos de la historia médica.
     * @return la historia médica actualizada.
     * @throws BadRequestException si la historia no existe o la petición es inválida.
     */
    @PutMapping(value = "/{id}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<HistoriaMedicaRS> actualizarHistoriaMedica(
            @PathVariable Long id, @RequestBody HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException;

    /**
     * Crea una nueva anotación dentro de una historia médica.
     *
     * @param anotacionHistoriaRQ datos de la anotación a crear.
     * @return la anotación creada, con su identificador asignado.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @PostMapping(value = "/anotaciones", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<AnotacionHistoriaRS> crearAnotacionHistoria(
            @RequestBody AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException;

    /**
     * Lista las anotaciones de una historia médica puntual, de la más
     * reciente a la más antigua.
     *
     * @param historiaId identificador de la historia médica.
     * @return listado de anotaciones de esa historia médica.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    @GetMapping(value = "/{historiaId}/anotaciones", produces = {"application/json"})
    ResponseEntity<List<AnotacionHistoriaRS>> listarAnotacionesHistoria(@PathVariable Long historiaId)
            throws BadRequestException;

    /**
     * Actualiza una anotación de historia médica ya existente.
     *
     * @param id                   identificador de la anotación a actualizar.
     * @param anotacionHistoriaRQ  nuevos datos de la anotación.
     * @return la anotación actualizada.
     * @throws BadRequestException si la anotación no existe o la petición es inválida.
     */
    @PutMapping(value = "/anotaciones/{id}", produces = {"application/json"}, consumes = {"application/json"})
    ResponseEntity<AnotacionHistoriaRS> actualizarAnotacionHistoria(
            @PathVariable Long id, @RequestBody AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException;
}
