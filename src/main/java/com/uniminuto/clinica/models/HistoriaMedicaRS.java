package com.uniminuto.clinica.models;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de respuesta usado para exponer una historia médica.
 */
@Data
public class HistoriaMedicaRS {

    /**
     * Identificador único de la historia médica.
     */
    private Long id;

    /**
     * Identificador del paciente (mascota) dueño de la historia médica.
     */
    private Long pacienteId;

    /**
     * Fecha y hora de creación de la historia médica.
     */
    private LocalDateTime fechaCreacion;
}
