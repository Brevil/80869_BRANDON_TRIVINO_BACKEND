package com.uniminuto.clinica.models;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de respuesta usado para exponer una anotación de historia médica.
 */
@Data
public class AnotacionHistoriaRS {

    /**
     * Identificador único de la anotación.
     */
    private Long id;

    /**
     * Identificador de la historia médica a la que pertenece la anotación.
     */
    private Long historiaId;

    /**
     * Identificador del médico que registró la anotación.
     */
    private Long medicoId;

    /**
     * Fecha y hora en que se registró la anotación.
     */
    private LocalDateTime fecha;

    /**
     * Contenido de la anotación clínica.
     */
    private String descripcion;
}
