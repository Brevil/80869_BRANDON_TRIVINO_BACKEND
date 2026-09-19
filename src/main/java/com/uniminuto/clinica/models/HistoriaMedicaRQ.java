package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * DTO de petición usado para crear o actualizar una historia médica.
 */
@Data
public class HistoriaMedicaRQ {

    /**
     * Identificador del paciente (mascota) dueño de la historia médica.
     */
    private Long pacienteId;
}
