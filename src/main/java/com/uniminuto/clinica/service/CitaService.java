package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.CitaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Operaciones de negocio sobre citas médicas.
 */
public interface CitaService {

    /**
     * @return todas las citas, de la más reciente a la más antigua.
     */
    List<Cita> listarCitas();

    /**
     * @param id id de la cita.
     * @return la cita solicitada.
     * @throws BadRequestException si la cita no existe (404).
     */
    Cita buscarCita(Long id);

    /**
     * @param fechaInicio inicio del rango.
     * @param fechaFin    fin del rango.
     * @return citas dentro del rango, ordenadas por fecha.
     * @throws BadRequestException si las fechas son nulas o el rango es inválido.
     */
    List<Cita> listarCitasRango(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws BadRequestException;

    /**
     * Agenda una cita validando cliente, mascota, veterinario y horarios.
     *
     * @param citaRq datos de la cita.
     * @return respuesta con el resultado de la operación.
     * @throws BadRequestException si alguna regla de negocio no se cumple.
     */
    MiRespuestaRS crearCita(CitaRq citaRq) throws BadRequestException;

    /**
     * Edita una cita según su estado actual. Las citas atendidas no se modifican.
     *
     * @param citaRq datos de la cita, incluido su id.
     * @return respuesta con el resultado de la operación.
     * @throws BadRequestException si la cita no existe o no puede modificarse.
     */
    MiRespuestaRS actualizarCita(CitaRq citaRq) throws BadRequestException;
}
