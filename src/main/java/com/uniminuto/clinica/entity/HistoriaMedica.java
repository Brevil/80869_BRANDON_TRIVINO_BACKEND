package com.uniminuto.clinica.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * Historia clínica de una consulta atendida. Cada cita atendida genera como máximo una historia.
 */
@Entity
@Table(name = "historia_medica")
@Data
public class HistoriaMedica {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @OneToOne
    @JoinColumn(name = "cita_id", nullable = false, unique = true)
    private Cita cita;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Mascota mascota;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "diagnostico", nullable = false, columnDefinition = "TEXT")
    private String diagnostico;

    @Column(name = "observaciones", columnDefinition = "TEXT")
    private String observaciones;
}
