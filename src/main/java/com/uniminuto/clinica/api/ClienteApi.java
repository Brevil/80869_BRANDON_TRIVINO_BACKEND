package com.uniminuto.clinica.api;

import com.uniminuto.clinica.entity.Cliente;
import com.uniminuto.clinica.exception.BadRequestException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/cliente")
public interface ClienteApi {

    @GetMapping(value = "/listar",
            produces = {"application/json"})
    ResponseEntity<List<Cliente>> listarClientes()
            throws BadRequestException;

    /**
     * Consulta un cliente por id.
     *
     * @param id id del cliente.
     * @return el cliente solicitado.
     * @throws BadRequestException si no existe (404).
     */
    @GetMapping(value = "/{id}",
            produces = {"application/json"})
    ResponseEntity<Cliente> buscarCliente(@PathVariable Long id)
            throws BadRequestException;
}
