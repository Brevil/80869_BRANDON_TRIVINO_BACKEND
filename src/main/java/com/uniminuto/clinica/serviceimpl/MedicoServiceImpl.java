package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Especializacion;
import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.models.EspecializacionRS;
import com.uniminuto.clinica.models.MedicoRS;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
import java.util.List;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementación de {@link MedicoService}.
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    /**
     * Repositorio JPA de médicos.
     */
    @Autowired
    private MedicoRepository medicoRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    public List<MedicoRS> listarMedicosConEspecializacion() throws BadRequestException {
        List<Medico> medicos = medicoRepository.findAllConEspecializacion();
        return medicos.stream().map(this::aMedicoRS).toList();
    }

    /**
     * Convierte una entidad {@link Medico} (con su especialización ya
     * cargada) en su DTO de respuesta {@link MedicoRS}.
     *
     * @param medico entidad leída de la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private MedicoRS aMedicoRS(Medico medico) {
        MedicoRS rs = new MedicoRS();
        rs.setId(medico.getId());
        rs.setTipoDocumento(medico.getTipoDocumento());
        rs.setNumeroDocumento(medico.getNumeroDocumento());
        rs.setNombres(medico.getNombres());
        rs.setApellidos(medico.getApellidos());
        rs.setTelefono(medico.getTelefono());
        rs.setRegistroProfesional(medico.getRegistroProfesional());
        rs.setEspecializacion(aEspecializacionRS(medico.getEspecializacion()));
        return rs;
    }

    /**
     * Convierte una entidad {@link Especializacion} en su DTO de respuesta.
     *
     * @param especializacion entidad leída de la base de datos.
     * @return DTO listo para ser expuesto por la API.
     */
    private EspecializacionRS aEspecializacionRS(Especializacion especializacion) {
        EspecializacionRS rs = new EspecializacionRS();
        rs.setId(especializacion.getId());
        rs.setNombre(especializacion.getNombre());
        rs.setDescripcion(especializacion.getDescripcion());
        rs.setCodigoEspecializacion(especializacion.getCodigoEspecializacion());
        return rs;
    }
}
