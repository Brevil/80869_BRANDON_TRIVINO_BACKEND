package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Medico;
import com.uniminuto.clinica.repository.MedicoRepository;
import com.uniminuto.clinica.service.MedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Consultas de veterinarios.
 */
@Service
public class MedicoServiceImpl implements MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Override
    public List<Medico> listarMedicos() {
        return medicoRepository.findAll(Sort.by("nombres", "apellidos"));
    }
}
