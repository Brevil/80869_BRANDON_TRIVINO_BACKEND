package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.models.FormulaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;

import java.util.List;

/**
 * Operaciones de negocio sobre fórmulas médicas.
 */
public interface FormulaService {

    /**
     * @return fórmulas ordenadas por fecha de creación descendente.
     */
    List<FormulaMedica> findAllFormulas();

    /**
     * @param id id de la fórmula.
     * @return la fórmula solicitada.
     */
    FormulaMedica buscarFormula(Long id);

    /**
     * @return medicamentos disponibles para prescribir.
     */
    List<Medicamento> listarMedicamentos();

    /**
     * Crea una fórmula validando sus relaciones y reglas de negocio.
     *
     * @param formulaMedicaRq datos de la fórmula.
     * @return respuesta con el resultado de la operación.
     */
    MiRespuestaRS crearFormula(FormulaMedicaRq formulaMedicaRq);

    /**
     * Edita una fórmula aplicando las mismas validaciones que al crearla.
     *
     * @param formulaMedicaRq datos de la fórmula, incluido su id.
     * @return respuesta con el resultado de la operación.
     */
    MiRespuestaRS actualizarFormula(FormulaMedicaRq formulaMedicaRq);
}
