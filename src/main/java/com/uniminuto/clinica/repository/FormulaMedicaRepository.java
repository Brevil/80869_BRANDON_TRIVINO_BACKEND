package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.FormulaMedica;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio Spring Data JPA para la entidad {@link FormulaMedica}.
 */
public interface FormulaMedicaRepository extends JpaRepository<FormulaMedica, Long> {

    /**
     * Lista todas las fórmulas médicas ordenadas por fecha de creación del
     * registro, de la más reciente a la más antigua.
     *
     * @return listado completo de fórmulas médicas ordenado descendentemente.
     */
    List<FormulaMedica> findAllByOrderByFechaCreacionRegistroDesc();
}
