package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Cita;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA para la entidad {@link Cita}.
 */
public interface CitaRepository extends JpaRepository<Cita, Long> {

    /**
     * Filtra las citas cuya {@code fechaHora} está dentro del rango
     * [{@code fechaInicial}, {@code fechaFinal}] (ambos inclusive) y las
     * ordena de la cita más reciente a la más antigua.
     *
     * @param fechaInicial límite inferior del rango de búsqueda.
     * @param fechaFinal   límite superior del rango de búsqueda.
     * @return listado de citas dentro del rango, ordenado descendentemente.
     */
    List<Cita> findByFechaHoraBetweenOrderByFechaHoraDesc(
            LocalDateTime fechaInicial, LocalDateTime fechaFinal);
}
