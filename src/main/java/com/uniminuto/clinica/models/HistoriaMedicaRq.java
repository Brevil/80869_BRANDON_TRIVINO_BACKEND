package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * Solicitud para crear una historia clínica a partir de una cita atendida.
 * Mascota, cliente, veterinario, fecha y motivo se toman de la cita para evitar inconsistencias.
 */
@Data
public class HistoriaMedicaRq {
    /** Id de la cita atendida. */
    private Long citaId;
    /** Diagnóstico de la consulta. */
    private String diagnostico;
    /** Observaciones generales de la consulta (opcional). */
    private String observaciones;
}
