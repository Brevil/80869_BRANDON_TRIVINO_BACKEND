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
 * Entidad JPA que representa la tabla {@code historia_medica}.
 * <p>
 * Una historia médica agrupa las {@link AnotacionHistoria} (notas clínicas)
 * registradas a lo largo del tiempo para un paciente (mascota). La relación
 * hacia las anotaciones no se mapea aquí como colección {@code @OneToMany}
 * para mantener el modelo simple: la anotación es quien conoce a su
 * historia (relación dueña) y se consulta a través de su propio repositorio.
 * </p>
 */
@Data
@Entity
@Table(name = "historia_medica")
public class HistoriaMedica {

    /**
     * Identificador único de la historia médica (llave primaria autoincremental).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    /**
     * Identificador del paciente (mascota) dueño de la historia médica.
     */
    @Column(name = "paciente_id", nullable = false)
    private Long pacienteId;

    /**
     * Fecha y hora de creación de la historia médica. Es el criterio usado
     * para ordenar el listado (de la más reciente a la más antigua) y para
     * filtrar por rango de fechas.
     */
    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;
}
