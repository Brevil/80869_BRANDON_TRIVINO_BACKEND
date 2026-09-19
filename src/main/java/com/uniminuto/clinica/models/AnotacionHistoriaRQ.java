package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * DTO de petición usado para crear o actualizar una anotación de historia
 * médica.
 */
@Data
public class AnotacionHistoriaRQ {

    /**
     * Identificador de la historia médica a la que pertenece la anotación.
     * Se requiere al crear; al actualizar puede omitirse porque la anotación
     * no cambia de historia.
     */
    private Long historiaId;

    /**
     * Identificador del médico que registra la anotación.
     */
    private Long medicoId;

    /**
     * Contenido de la anotación clínica.
     */
    private String descripcion;
}
