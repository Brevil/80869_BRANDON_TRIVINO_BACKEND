package com.uniminuto.clinica.models;

import lombok.Data;

/**
 * Solicitud para agregar una anotación a una historia clínica.
 */
@Data
public class AnotacionHistoriaRq {
    /** Id de la historia clínica a la que pertenece la anotación. */
    private Long historiaId;
    /** Id del veterinario que registra la anotación. */
    private Long medicoId;
    /** Descripción de lo observado o realizado. */
    private String descripcion;
    /** Observación adicional (opcional). */
    private String observacion;
    /** Diagnóstico o evolución del paciente. */
    private String diagnostico;
}
