package com.uniminuto.clinica.models;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Solicitud para crear o editar una cita.
 */
@Data
public class CitaRq {

    /** Id de la cita. Obligatorio solo al editar. */
    private Long id;
    /** Id del cliente que solicita la cita. */
    private Long clienteId;
    /** Id de la mascota. Debe pertenecer al cliente. */
    private Integer mascotaId;
    /** Id del veterinario. */
    private Long medicoId;
    /** Fecha y hora de la cita. */
    private LocalDateTime fechaHora;
    /** Motivo de la consulta. */
    private String motivo;
    /** Estado de la cita: PENDIENTE, CONFIRMADA, ATENDIDA, CANCELADA o PROGRAMADA. */
    private String estado;
}
