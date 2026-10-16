package com.uniminuto.clinica.models;

import lombok.Data;

import java.time.LocalDate;

/**
 * Solicitud para crear o editar una fórmula médica.
 */
@Data
public class FormulaMedicaRq {
    /** Id de la fórmula. Obligatorio solo al editar. */
    private Long id;
    /** Id de la cita (consulta) asociada. */
    private Long citaId;
    /** Id de la mascota. Debe ser la misma mascota de la cita. */
    private Integer mascotaId;
    /** Id del veterinario que emite la fórmula. */
    private Long medicoId;
    /** Id del medicamento prescrito. */
    private Long medicamentoId;
    /** Fecha de emisión. No puede ser anterior a la fecha de la cita. */
    private LocalDate fechaEmision;
    /** Dosis prescrita. */
    private String dosis;
    /** Frecuencia de administración, por ejemplo "cada 8 horas". */
    private String frecuencia;
    /** Duración del tratamiento en días. */
    private Integer duracionDias;
    /** Indicaciones adicionales (opcional). */
    private String indicaciones;
}
