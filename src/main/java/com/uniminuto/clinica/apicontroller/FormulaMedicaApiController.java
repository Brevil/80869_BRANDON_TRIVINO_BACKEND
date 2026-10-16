package com.uniminuto.clinica.apicontroller;

import com.uniminuto.clinica.api.FormulaMedicaApi;
import com.uniminuto.clinica.entity.FormulaMedica;
import com.uniminuto.clinica.entity.Medicamento;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.models.FormulaMedicaRq;
import com.uniminuto.clinica.models.MiRespuestaRS;
import com.uniminuto.clinica.service.FormulaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Controlador REST de fórmulas médicas; delega toda la lógica en {@link FormulaService}.
 */
@RestController
public class FormulaMedicaApiController implements FormulaMedicaApi {

    @Autowired
    private FormulaService formulaService;

    @Override
    public ResponseEntity<List<FormulaMedica>> listarFormulas() {
        return ResponseEntity.ok(this.formulaService.findAllFormulas());
    }

    @Override
    public ResponseEntity<FormulaMedica> buscarFormula(Long id) throws BadRequestException {
        return ResponseEntity.ok(this.formulaService.buscarFormula(id));
    }

    @Override
    public ResponseEntity<List<Medicamento>> listarMedicamentos() {
        return ResponseEntity.ok(this.formulaService.listarMedicamentos());
    }

    @Override
    public ResponseEntity<MiRespuestaRS> crearFormula(FormulaMedicaRq formula) throws BadRequestException {
        return ResponseEntity.ok(this.formulaService.crearFormula(formula));
    }

    @Override
    public ResponseEntity<MiRespuestaRS> actualizarFormula(FormulaMedicaRq formula) throws BadRequestException {
        return ResponseEntity.ok(this.formulaService.actualizarFormula(formula));
    }
}
