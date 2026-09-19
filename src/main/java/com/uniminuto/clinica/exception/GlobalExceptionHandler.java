package com.uniminuto.clinica.exception;

import com.uniminuto.clinica.models.MiRespuestaRS;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Manejador global de excepciones de la API.
 * <p>
 * Traduce las excepciones lanzadas por los servicios en respuestas HTTP con
 * el código y el cuerpo adecuado, en lugar de dejar que se propaguen como un
 * error genérico 500.
 * </p>
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Captura cualquier {@link BadRequestException} lanzada por los
     * servicios (datos obligatorios faltantes, rangos de fecha inválidos,
     * identificadores que no existen, etc.) y la traduce a un
     * {@code 400 Bad Request} con un cuerpo {@link MiRespuestaRS} explicando
     * el motivo.
     *
     * @param ex excepción capturada.
     * @return respuesta HTTP 400 con el mensaje de error.
     */
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<MiRespuestaRS> manejarBadRequestException(BadRequestException ex) {
        MiRespuestaRS rta = new MiRespuestaRS();
        rta.setStatus(HttpStatus.BAD_REQUEST.value());
        rta.setMessage(ex.getMessage());
        return ResponseEntity.badRequest().body(rta);
    }
}
