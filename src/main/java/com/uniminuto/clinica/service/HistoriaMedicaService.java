package com.uniminuto.clinica.service;

import com.uniminuto.clinica.models.AnotacionHistoriaRQ;
import com.uniminuto.clinica.models.AnotacionHistoriaRS;
import com.uniminuto.clinica.models.HistoriaMedicaRQ;
import com.uniminuto.clinica.models.HistoriaMedicaRS;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Reglas de negocio para la historia médica y sus anotaciones.
 */
public interface HistoriaMedicaService {

    /**
     * Crea una nueva historia médica.
     *
     * @param historiaMedicaRQ datos de la historia médica a crear.
     * @return la historia médica creada.
     * @throws BadRequestException si los datos de la petición son inválidos.
     */
    HistoriaMedicaRS crearHistoriaMedica(HistoriaMedicaRQ historiaMedicaRQ) throws BadRequestException;

    /**
     * Lista las historias médicas creadas entre una fecha inicial y una
     * fecha final, ordenadas de la más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango (inclusive).
     * @param fechaFinal   límite superior del rango (inclusive).
     * @return listado de historias médicas dentro del rango solicitado.
     * @throws BadRequestException si el rango de fechas es inválido.
     */
    List<HistoriaMedicaRS> listarHistoriasMedicas(LocalDateTime fechaInicial, LocalDateTime fechaFinal)
            throws BadRequestException;

    /**
     * Actualiza una historia médica ya existente.
     *
     * @param id               identificador de la historia médica a actualizar.
     * @param historiaMedicaRQ nuevos datos de la historia médica.
     * @return la historia médica actualizada.
     * @throws BadRequestException si la historia no existe o los datos son inválidos.
     */
    HistoriaMedicaRS actualizarHistoriaMedica(Long id, HistoriaMedicaRQ historiaMedicaRQ)
            throws BadRequestException;

    /**
     * Crea una nueva anotación dentro de una historia médica.
     *
     * @param anotacionHistoriaRQ datos de la anotación a crear.
     * @return la anotación creada.
     * @throws BadRequestException si la historia médica referenciada no existe.
     */
    AnotacionHistoriaRS crearAnotacionHistoria(AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException;

    /**
     * Lista las anotaciones de una historia médica puntual, de la más
     * reciente a la más antigua.
     *
     * @param historiaId identificador de la historia médica.
     * @return listado de anotaciones de esa historia médica.
     * @throws BadRequestException si la historia médica no existe.
     */
    List<AnotacionHistoriaRS> listarAnotacionesHistoria(Long historiaId) throws BadRequestException;

    /**
     * Actualiza una anotación de historia médica ya existente.
     *
     * @param id                  identificador de la anotación a actualizar.
     * @param anotacionHistoriaRQ nuevos datos de la anotación.
     * @return la anotación actualizada.
     * @throws BadRequestException si la anotación no existe o los datos son inválidos.
     */
    AnotacionHistoriaRS actualizarAnotacionHistoria(Long id, AnotacionHistoriaRQ anotacionHistoriaRQ)
            throws BadRequestException;
}
