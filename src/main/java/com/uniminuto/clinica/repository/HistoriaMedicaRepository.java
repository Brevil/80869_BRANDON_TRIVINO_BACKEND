package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import com.uniminuto.clinica.entity.HistoriaMedica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Acceso a datos de historias clínicas.
 */
@Repository
public interface HistoriaMedicaRepository extends JpaRepository<HistoriaMedica, Long> {

    /**
     * @return todas las historias, de la más reciente a la más antigua.
     */
    List<HistoriaMedica> findAllByOrderByFechaHoraDesc();

    /**
     * @param cita cita atendida.
     * @return la historia generada por la cita, si existe.
     */
    Optional<HistoriaMedica> findByCita(Cita cita);
}
