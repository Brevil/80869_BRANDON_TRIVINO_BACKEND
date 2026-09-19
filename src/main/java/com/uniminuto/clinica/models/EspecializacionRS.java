package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * DTO de respuesta usado para exponer la especialización de un médico,
 * anidado dentro de {@link MedicoRS}.
 */
@Data
public class EspecializacionRS {

    /**
     * Identificador único de la especialización.
     */
    private Long id;

    /**
     * Nombre de la especialización.
     */
    private String nombre;

    /**
     * Descripción de la especialización.
     */
    private String descripcion;

    /**
     * Código corto y único de la especialización.
     */
    private String codigoEspecializacion;
}
