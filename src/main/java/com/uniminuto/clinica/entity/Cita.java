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
 * Entidad JPA que representa la tabla {@code cita}.
 * <p>
 * Guarda la información de una cita agendada entre un cliente (dueño de la
 * mascota), su mascota y el médico veterinario que la atiende. Las llaves
 * foráneas ({@code cliente_id}, {@code mascota_id}, {@code medico_id}) se
 * mapean como valores simples ({@link Long}) y no como relaciones JPA
 * completas, ya que los servicios solicitados no requieren navegar hacia
 * esas entidades; esto mantiene el modelo simple.
 * </p>
 */
@Data
@Entity
@Table(name = "cita")
public class Cita {

    /**
     * Identificador único de la cita (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del cliente (dueño de la mascota) que agenda la cita.
     */
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    /**
     * Identificador de la mascota atendida en la cita.
     */
    @Column(name = "mascota_id", nullable = false)
    private Long mascotaId;

    /**
     * Identificador del médico veterinario que atiende la cita.
     */
    @Column(name = "medico_id", nullable = false)
    private Long medicoId;

    /**
     * Fecha y hora en la que está programada la cita.
     */
    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    /**
     * Estado actual de la cita (ej: {@code programada}, {@code cancelada},
     * {@code completada}).
     */
    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    /**
     * Motivo o descripción de la consulta.
     */
    @Column(name = "motivo")
    private String motivo;
}
