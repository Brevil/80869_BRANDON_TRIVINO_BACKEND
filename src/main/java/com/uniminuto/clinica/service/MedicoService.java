package com.uniminuto.clinica.service;

import com.uniminuto.clinica.models.MedicoRS;
import java.util.List;
import org.apache.coyote.BadRequestException;

/**
 * Reglas de negocio para la consulta de médicos.
 */
public interface MedicoService {

    /**
     * Obtiene todos los médicos junto con su especialización.
     *
     * @return listado de médicos mapeado a DTO de respuesta.
     * @throws BadRequestException si ocurre un error al procesar la petición.
     */
    List<MedicoRS> listarMedicosConEspecializacion() throws BadRequestException;
}
