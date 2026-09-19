package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * DTO de respuesta usado para exponer un médico junto con su especialización.
 */
@Data
public class MedicoRS {

    /**
     * Identificador único del médico.
     */
    private Long id;

    /**
     * Tipo de documento de identidad del médico.
     */
    private String tipoDocumento;

    /**
     * Número de documento de identidad del médico.
     */
    private String numeroDocumento;

    /**
     * Nombres del médico.
     */
    private String nombres;

    /**
     * Apellidos del médico.
     */
    private String apellidos;

    /**
     * Teléfono de contacto del médico.
     */
    private String telefono;

    /**
     * Número de registro profesional del médico.
     */
    private String registroProfesional;

    /**
     * Especialización médica del médico.
     */
    private EspecializacionRS especializacion;
}
