package com.uniminuto.clinica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * Entidad JPA que representa la tabla {@code anotacion_historia}.
 * <p>
 * Cada anotación es una nota clínica que un médico agrega a la
 * {@link HistoriaMedica} de un paciente. Es el lado dueño de la relación
 * ({@code @ManyToOne}) hacia {@code historia_medica}, a través de la columna
 * {@code historia_id}.
 * </p>
 */
@Data
@Entity
@Table(name = "anotacion_historia")
public class AnotacionHistoria {

    /**
     * Identificador único de la anotación (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Historia médica a la que pertenece esta anotación.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "historia_id", nullable = false)
    private HistoriaMedica historia;

    /**
     * Identificador del médico que registra la anotación.
     */
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    /**
     * Fecha y hora en que se registró la anotación.
     */
    @Column(name = "fecha")
    private LocalDateTime fecha;

    /**
     * Contenido de la anotación clínica.
     */
    @Column(name = "descripcion", nullable = false)
    private String descripcion;
}
