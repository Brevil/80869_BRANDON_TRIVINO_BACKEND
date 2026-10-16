package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Acceso a datos de anotaciones de historias clínicas.
 */
@Repository
public interface AnotacionHistoriaRepository extends JpaRepository<AnotacionHistoria, Long> {

    /**
     * @param historiaId id de la historia clínica.
     * @return anotaciones de la historia, de la más reciente a la más antigua.
     */
    List<AnotacionHistoria> findByHistoriaIdOrderByFechaDesc(Long historiaId);

    /**
     * @param mascotaId id de la mascota.
     * @return anotaciones de todas las historias de la mascota, de la más reciente a la más antigua.
     */
    List<AnotacionHistoria> findByHistoriaMascotaMascotaIdOrderByFechaDesc(Integer mascotaId);
}
