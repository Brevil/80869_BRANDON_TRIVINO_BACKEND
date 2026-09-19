package com.uniminuto.clinica.repository;

import com.uniminuto.clinica.entity.Medico;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repositorio Spring Data JPA para la entidad {@link Medico}.
 */
public interface MedicoRepository extends JpaRepository<Medico, Long> {

    /**
     * Lista todos los médicos junto con su especialización, usando
     * {@code JOIN FETCH} para traer ambas entidades en una sola consulta
     * (evita el problema de N+1 consultas al leer {@code medico.especializacion}
     * de forma perezosa fuera de una transacción).
     *
     * @return listado completo de médicos con su especialización cargada.
     */
    @Query("SELECT m FROM Medico m JOIN FETCH m.especializacion")
    List<Medico> findAllConEspecializacion();
}
