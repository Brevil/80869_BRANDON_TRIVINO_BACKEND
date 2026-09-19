package com.uniminuto.clinica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Entidad JPA que representa la tabla {@code formula_medica}.
 * <p>
 * Una fórmula médica es el medicamento y la dosis formulados a una mascota
 * dentro de una {@code cita}, y forma parte del inventario que se debe
 * poder listar ordenado por fecha de creación.
 * </p>
 */
@Data
@Entity
@Table(name = "formula_medica")
public class FormulaMedica {

    /**
     * Identificador único de la fórmula médica (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador de la cita en la que se generó la fórmula.
     */
    @Column(name = "cita_id", nullable = false)
    private Long citaId;

    /**
     * Identificador del medicamento formulado.
     */
    @Column(name = "medicamento_id", nullable = false)
    private Long medicamentoId;

    /**
     * Dosis formulada (ej: {@code "500 mg cada 8 horas"}).
     */
    @Column(name = "dosis", nullable = false)
    private String dosis;

    /**
     * Indicaciones adicionales para la administración del medicamento.
     */
    @Column(name = "indicaciones")
    private String indicaciones;

    /**
     * Fecha y hora en que se creó el registro de la fórmula.
     * usado para ordenar el listado (de la más reciente a la más antigua).
     */
    @Column(name = "fecha_creacion_registro", nullable = false)
    private LocalDateTime fechaCreacionRegistro;

    /**
     * Fecha y hora de la última actualización del registro, si aplica.
     */
    @Column(name = "fecha_actualizacion_registro")
    private LocalDateTime fechaActualizacionRegistro;
}
