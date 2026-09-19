package com.uniminuto.clinica.service;

import com.uniminuto.clinica.models.CitaRQ;
import com.uniminuto.clinica.models.CitaRS;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Reglas de negocio para la gestión de citas.
 */
public interface CitaService {

    /**
     * Filtra las citas entre una fecha inicial y una fecha final, ordenadas
     * de la más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango (inclusive).
     * @param fechaFinal   límite superior del rango (inclusive).
     * @return listado de citas dentro del rango solicitado.
     * @throws BadRequestException si el rango de fechas es inválido.
     */
    List<CitaRS> filtrarCitasPorFecha(LocalDateTime fechaInicial, LocalDateTime fechaFinal)
            throws BadRequestException;

    /**
     * Crea una nueva cita en el sistema.
     *
     * @param citaRQ datos de la cita a crear.
     * @return la cita creada, con su identificador asignado.
     * @throws BadRequestException si los datos de la petición son inválidos.
     */
    CitaRS crearCita(CitaRQ citaRQ) throws BadRequestException;

    /**
     * Actualiza una cita ya existente en el sistema.
     *
     * @param id     identificador de la cita a actualizar.
     * @param citaRQ nuevos datos de la cita.
     * @return la cita actualizada.
     * @throws BadRequestException si la cita no existe o los datos son inválidos.
     */
    CitaRS actualizarCita(Long id, CitaRQ citaRQ) throws BadRequestException;
}
