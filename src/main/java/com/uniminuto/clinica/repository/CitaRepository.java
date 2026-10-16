package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.Mascota;
import com.uniminuto.clinica.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Acceso a datos de citas.
 */
@Repository
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * @return todas las citas, de la más reciente a la más antigua.
     */
    List<Cita> findAllByOrderByFechaHoraDesc();

    /**
     * @param fechaInicio inicio del rango (inclusive).
     * @param fechaFin    fin del rango (inclusive).
     * @return citas dentro del rango.
     */
    List<Cita> findByFechaHoraBetween(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * @param medico      veterinario.
     * @param fechaInicio inicio del rango (inclusive).
     * @param fechaFin    fin del rango (inclusive).
     * @return citas del veterinario dentro del rango.
     */
    List<Cita> findByMedicoAndFechaHoraBetween(Medico medico, LocalDateTime fechaInicio, LocalDateTime fechaFin);

    /**
     * @param mascota     mascota.
     * @param fechaInicio inicio del rango (inclusive).
     * @param fechaFin    fin del rango (inclusive).
     * @return citas de la mascota dentro del rango.
     */
    List<Cita> findByMascotaAndFechaHoraBetween(Mascota mascota, LocalDateTime fechaInicio, LocalDateTime fechaFin);

}
