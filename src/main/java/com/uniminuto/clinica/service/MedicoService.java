package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Medico;

import java.util.List;

/**
 * Consultas de veterinarios.
 */
public interface MedicoService {

    /**
     * @return veterinarios ordenados por nombre.
     */
    List<Medico> listarMedicos();
}
