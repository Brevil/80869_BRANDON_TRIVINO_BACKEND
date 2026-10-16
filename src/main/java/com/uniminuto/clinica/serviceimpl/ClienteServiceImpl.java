package com.uniminuto.clinica.serviceimpl;

import com.uniminuto.clinica.entity.Cliente;
import com.uniminuto.clinica.exception.BadRequestException;
import com.uniminuto.clinica.repository.ClienteRepository;
import com.uniminuto.clinica.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Consultas de clientes.
 */
@Service
public class ClienteServiceImpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    /**
     * @return clientes ordenados por nombre, sin distinguir mayúsculas.
     */
    @Override
    public List<Cliente> listarClientes() throws BadRequestException {
        return clienteRepository.findAll().stream()
                .sorted((c1, c2) -> c1.getNombres().compareToIgnoreCase(c2.getNombres()))
                .toList();
    }

    /**
     * @param id id del cliente.
     * @return el cliente solicitado.
     */
    @Override
    public Cliente buscarCliente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("El cliente no existe", HttpStatus.NOT_FOUND));
    }
}
