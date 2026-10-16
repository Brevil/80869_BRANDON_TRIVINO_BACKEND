package com.uniminuto.clinica.service;

import com.uniminuto.clinica.entity.Cliente;
import com.uniminuto.clinica.exception.BadRequestException;

import java.util.List;

/**
 * Consultas de clientes.
 */
public interface ClienteService {

    /**
     * @return clientes ordenados por nombre.
     * @throws BadRequestException en caso de error de validación.
     */
    List<Cliente> listarClientes() throws BadRequestException;

    /**
     * @param id id del cliente.
     * @return el cliente solicitado.
     * @throws BadRequestException si no existe (404).
     */
    Cliente buscarCliente(Long id);
}
