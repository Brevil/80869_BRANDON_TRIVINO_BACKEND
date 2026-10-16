package com.uniminuto.clinica.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Cita médica agendada para una mascota con un veterinario.
 */
@Entity
@Table(name = "cita")
@Data
public class Cita {

    /** Estado inicial de una cita recién agendada. */
    public static final String ESTADO_PENDIENTE = "PENDIENTE";
    /** Cita confirmada por el cliente. */
    public static final String ESTADO_CONFIRMADA = "CONFIRMADA";
    /** Cita ya atendida por el veterinario. Su información histórica no se modifica. */
    public static final String ESTADO_ATENDIDA = "ATENDIDA";
    /** Cita cancelada. No cuenta para cruces de horario y no se modifica como activa. */
    public static final String ESTADO_CANCELADA = "CANCELADA";
    /** Cita programada. */
    public static final String ESTADO_PROGRAMADA = "PROGRAMADA";
    /** Estados válidos para una cita. */
    public static final List<String> ESTADOS = List.of(
            ESTADO_PENDIENTE, ESTADO_CONFIRMADA, ESTADO_ATENDIDA, ESTADO_CANCELADA, ESTADO_PROGRAMADA);

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "mascota_id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;
}
