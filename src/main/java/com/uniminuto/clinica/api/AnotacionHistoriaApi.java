package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.AnotacionHistoriaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Contrato REST de anotaciones de historias clínicas.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/anotacion-historia")
public interface AnotacionHistoriaApi {

    /**
     * Agrega una anotación a una historia clínica.
     *
     * @param anotacionHistoria datos de la anotación.
     * @return respuesta con el resultado.
     * @throws BadRequestException si no cumple las reglas de negocio.
     */
    @PostMapping(value = "/crear",
            produces = {"application/json"},
            consumes = {"application/json"})
    ResponseEntity<MiRespuestaRS> crearAnotacionHistoria(
            @RequestBody AnotacionHistoriaRq anotacionHistoria
    )
            throws BadRequestException;

    /**
     * Consulta anotaciones por historia o, si no se indica, por mascota.
     *
     * @param historiaId id de la historia (opcional).
     * @param mascotaId  id de la mascota (opcional).
     * @return anotaciones de la más reciente a la más antigua.
     * @throws BadRequestException si no se indica ni historia ni mascota.
     */
    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<AnotacionHistoria>> listarAnotaciones(
            @RequestParam(required = false) Long historiaId,
            @RequestParam(required = false) Integer mascotaId
    )
            throws BadRequestException;
}
