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
import lombok.Data;

/**
 * Entidad JPA que representa la tabla {@code medico}.
 * <p>
 * Cada médico tiene una única {@link Especializacion} asociada a través de la
 * columna {@code especializacion_id}. La relación se mapea como
 * {@code @ManyToOne} porque varios médicos pueden compartir la misma
 * especialización.
 * </p>
 */
@Data
@Entity
@Table(name = "medico")
public class Medico {

    /**
     * Identificador único del médico (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Tipo de documento de identidad del médico (ej: {@code CC}, {@code CE}).
     */
    @Column(name = "tipo_documento", nullable = false, length = 10)
    private String tipoDocumento;

    /**
     * Número de documento de identidad del médico. Es único.
     */
    @Column(name = "numero_documento", nullable = false, length = 20)
    private String numeroDocumento;

    /**
     * Nombres del médico.
     */
    @Column(name = "nombres", nullable = false, length = 100)
    private String nombres;

    /**
     * Apellidos del médico.
     */
    @Column(name = "apellidos", nullable = false, length = 100)
    private String apellidos;

    /**
     * Teléfono de contacto del médico.
     */
    @Column(name = "telefono", length = 20)
    private String telefono;

    /**
     * Número de registro profesional (tarjeta profesional) del médico. Es único.
     */
    @Column(name = "registro_profesional", nullable = false, length = 50)
    private String registroProfesional;

    /**
     * Especialización médica asociada al médico.
     * <p>
     * Se usa {@link FetchType#LAZY} por buena práctica; el listado que
     * requiere traer la especialización usa una consulta con
     * {@code JOIN FETCH} para evitar el problema de N+1 consultas.
     * </p>
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especializacion_id", nullable = false)
    private Especializacion especializacion;
}
