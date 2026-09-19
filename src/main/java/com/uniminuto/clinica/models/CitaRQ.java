package com.uniminuto.clinica.models;

import java.time.LocalDateTime;
import lombok.Data;

/**
 * DTO de petición usado para crear o actualizar una cita.
 * <p>
 * El campo {@code id} no se envía al crear (lo genera la base de datos); al
 * actualizar, el identificador de la cita a modificar viaja en la URL, no en
 * este cuerpo.
 * </p>
 */
@Data
public class CitaRQ {

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
     * Estado actual de la cita (ej: {@code programada}, {@code cancelada},
     * {@code completada}).
     */
    private String estado;

    /**
     * Motivo o descripción de la consulta.
     */
    private String motivo;
}
