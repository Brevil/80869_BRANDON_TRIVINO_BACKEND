package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.models.FormulaMedicaRS;
import com.uniminuto.clinica.repository.FormulaMedicaRepository;
import com.uniminuto.clinica.service.FormulaMedicaService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link FormulaMedicaService}.
 */
@Service
public class FormulaMedicaServiceImpl implements FormulaMedicaService {

    /**
     * Repositorio JPA de fórmulas médicas.
     */
    @Autowired
    private FormulaMedicaRepository formulaMedicaRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<FormulaMedicaRS> listarFormulasMedicas() throws BadRequestException {
        List<FormulaMedica> formulas =
                formulaMedicaRepository.findAllByOrderByFechaCreacionRegistroDesc();
        return formulas.stream()
                .map(this::aFormulaMedicaRS)
                .toList();
    }

    /**
     * Convierte una entidad {@link FormulaMedica} en su DTO de respuesta
     * {@link FormulaMedicaRS}.
     *
     * @param formula entidad leída de la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private FormulaMedicaRS aFormulaMedicaRS(FormulaMedica formula) {
        FormulaMedicaRS rs = new FormulaMedicaRS();
        rs.setId(formula.getId());
        rs.setCitaId(formula.getCitaId());
        rs.setMedicamentoId(formula.getMedicamentoId());
        rs.setDosis(formula.getDosis());
        rs.setIndicaciones(formula.getIndicaciones());
        rs.setFechaCreacionRegistro(formula.getFechaCreacionRegistro());
        rs.setFechaActualizacionRegistro(formula.getFechaActualizacionRegistro());
        return rs;
    }
}
