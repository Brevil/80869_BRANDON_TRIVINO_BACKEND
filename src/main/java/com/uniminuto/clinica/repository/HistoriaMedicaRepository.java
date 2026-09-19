package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.HistoriaMedica;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA para la entidad {@link HistoriaMedica}.
 */
public interface HistoriaMedicaRepository extends JpaRepository<HistoriaMedica, Long> {

    /**
     * Filtra las historias médicas cuya {@code fechaCreacion} está dentro del
     * rango [{@code fechaInicial}, {@code fechaFinal}] (ambos inclusive) y
     * las ordena de la más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango de búsqueda.
     * @param fechaFinal   límite superior del rango de búsqueda.
     * @return listado de historias médicas dentro del rango, ordenado descendentemente.
     */
    List<HistoriaMedica> findByFechaCreacionBetweenOrderByFechaCreacionDesc(
            LocalDateTime fechaInicial, LocalDateTime fechaFinal);
}
