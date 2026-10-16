package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import com.uniminuto.clinica.models.AnotacionHistoriaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;

import java.util.List;

/**
 * Operaciones de negocio sobre anotaciones de historias clínicas.
 */
public interface AnotacionHistoriaService {

    /**
     * Agrega una anotación a una historia clínica existente.
     *
     * @param anotacionHistoriaRq datos de la anotación.
     * @return respuesta con el resultado de la operación.
     */
    MiRespuestaRS crearAnotacionHistoria(AnotacionHistoriaRq anotacionHistoriaRq);

    /**
     * Consulta anotaciones por historia o, si no se indica, por mascota.
     *
     * @param historiaId id de la historia (opcional).
     * @param mascotaId  id de la mascota (opcional, se usa si no se indica historia).
     * @return anotaciones de la más reciente a la más antigua.
     */
    List<AnotacionHistoria> listarAnotaciones(Long historiaId, Integer mascotaId);
}
