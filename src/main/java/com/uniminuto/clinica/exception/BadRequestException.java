package com.uniminuto.clinica.exception;

import org.springframework.http.HttpStatus;

/**
 * Excepción de negocio o validación. Por defecto responde 400; puede indicar otro estado HTTP
 * (por ejemplo 404 cuando el registro no existe).
 */
public class BadRequestException extends RuntimeException {

    private final HttpStatus status;

    /**
     * Crea una excepción con estado 400 (Bad Request).
     *
     * @param message mensaje comprensible para el usuario.
     */
    public BadRequestException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Crea una excepción con el estado HTTP indicado.
     *
     * @param message mensaje comprensible para el usuario.
     * @param status  estado HTTP a responder.
     */
    public BadRequestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    /**
     * @return estado HTTP asociado a la excepción.
     */
    public HttpStatus getStatus() {
        return status;
    }
}
