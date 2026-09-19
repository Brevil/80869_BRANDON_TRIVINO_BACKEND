package com.uniminuto.clinica.models;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de respuesta usado para exponer una fórmula médica al listar el
 * inventario de fórmulas.
 */
@Data
public class FormulaMedicaRS {

    /**
     * Identificador único de la fórmula médica.
     */
    private Long id;

    /**
     * Identificador de la cita en la que se generó la fórmula.
     */
    private Long citaId;

    /**
     * Identificador del medicamento formulado.
     */
    private Long medicamentoId;

    /**
     * Dosis formulada.
     */
    private String dosis;

    /**
     * Indicaciones adicionales para la administración del medicamento.
     */
    private String indicaciones;

    /**
     * Fecha y hora de creación del registro.
     */
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Fecha y hora de la última actualización del registro, si aplica.
     */
    private LocalDateTime fechaActualizacionRegistro;
}
