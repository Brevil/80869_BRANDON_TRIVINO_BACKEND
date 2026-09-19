package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.AnotacionHistoria;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA para la entidad {@link AnotacionHistoria}.
 */
public interface AnotacionHistoriaRepository extends JpaRepository<AnotacionHistoria, Long> {

    /**
     * Lista las anotaciones de una historia médica puntual, de la más
     * reciente a la más antigua.
     *
     * @param historiaId identificador de la historia médica.
     * @return listado de anotaciones de esa historia, ordenado descendentemente.
     */
    List<AnotacionHistoria> findByHistoria_IdOrderByFechaDesc(Long historiaId);
}
