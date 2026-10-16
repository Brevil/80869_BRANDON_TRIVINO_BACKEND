package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.HistoriaMedica;
import com.uniminuto.clinica.models.HistoriaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;

import java.util.List;

/**
 * Operaciones de negocio sobre historias clínicas.
 */
public interface HistoriaMedicaService {

    /**
     * @return todas las historias, de la más reciente a la más antigua.
     */
    List<HistoriaMedica> listarHistorias();

    /**
     * Crea la historia clínica de una cita atendida.
     *
     * @param historiaMedicaRq id de la cita, diagnóstico y observaciones.
     * @return respuesta con el resultado de la operación.
     */
    MiRespuestaRS crearHistoria(HistoriaMedicaRq historiaMedicaRq);
}
