package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Medicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Acceso a datos de medicamentos.
 */
@Repository
public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    /**
     * @return medicamentos ordenados por nombre.
     */
    List<Medicamento> findAllByOrderByNombreAsc();
}
