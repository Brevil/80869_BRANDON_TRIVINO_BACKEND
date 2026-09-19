package com.uniminuto.clinica.service;

import com.uniminuto.clinica.models.FormulaMedicaRS;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Reglas de negocio para el inventario de fórmulas médicas.
 */
public interface FormulaMedicaService {

    /**
     * Obtiene todas las fórmulas médicas ordenadas por fecha de creación de
     * la más reciente a la más antigua.
     *
     * @return listado de fórmulas médicas mapeado a DTO de respuesta.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    List<FormulaMedicaRS> listarFormulasMedicas() throws BadRequestException;
}
