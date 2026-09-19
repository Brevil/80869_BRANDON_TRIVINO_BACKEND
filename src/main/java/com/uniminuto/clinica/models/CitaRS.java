package com.uniminuto.clinica.models;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de respuesta usado para exponer una cita al consultarla, filtrarla,
 * crearla o actualizarla.
 */
@Data
public class CitaRS {

    /**
     * Identificador único de la cita.
     */
    private Long id;

    /**
     * Identificador del cliente (dueño de la mascota) que agenda la cita.
     */
    private Long clienteId;

    /**
     * Identificador de la mascota atendida en la cita.
     */
    private Long mascotaId;

    /**
     * Identificador del médico veterinario que atiende la cita.
     */
    private Long medicoId;

    /**
     * Fecha y hora en la que está programada la cita.
     */
    private LocalDateTime fechaHora;

    /**
     * Estado actual de la cita.
     */
    private String estado;

    /**
     * Motivo o descripción de la consulta.
     */
    private String motivo;
}
