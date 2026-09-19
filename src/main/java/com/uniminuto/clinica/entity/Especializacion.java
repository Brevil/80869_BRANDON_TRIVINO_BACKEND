package com.uniminuto.clinica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

/**
 * Entidad JPA que representa la tabla {@code especializacion}.
 * <p>
 * Una especialización corresponde al área médica en la que un {@link Medico}
 * está certificado (por ejemplo: Cardiología, Dermatología, etc).
 * </p>
 */
@Data
@Entity
@Table(name = "especializacion")
public class Especializacion {

    /**
     * Identificador único de la especialización (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Nombre de la especialización. Es único en la base de datos.
     */
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    /**
     * Descripción libre de en qué consiste la especialización.
     */
    @Column(name = "descripcion")
    private String descripcion;

    /**
     * Código corto y único que identifica la especialización (ej: {@code COD_05}).
     */
    @Column(name = "codigo_especializacion", nullable = false, length = 10)
    private String codigoEspecializacion;
}
