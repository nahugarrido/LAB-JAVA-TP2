package com.neoris.turnosrotativos.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> responseBody = new LinkedHashMap<>();

        String error = String.valueOf(ex.getBindingResult().getFieldErrors().get(0).getDefaultMessage());

        responseBody.put("message", error);

        return new ResponseEntity<>(responseBody, headers, status);
    }

    @ExceptionHandler(NumberFormatException.class)
    public ResponseEntity<Object> handleNumberFormatException(
            NumberFormatException ex, WebRequest request
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message","El valor proporcionado no es un número válido.");

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EdadMinimaNoValidaException.class)
    public ResponseEntity<Object> handleEdadMinimaNoValidaException(
            EdadMinimaNoValidaException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmpleadoExistenteException.class)
    public ResponseEntity<Object> handleEmpleadoExistenteException(
            EmpleadoExistenteException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EmpleadoNoEncontradoException.class)
    public ResponseEntity<Object> handleEmpleadoNoEncontradoException(
            EmpleadoNoEncontradoException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EntidadNoEncontradaException.class)
    public ResponseEntity<Object> handleEntidadNoEncontradaException(
            EntidadNoEncontradaException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(hsTrabajadasNoValidaException.class)
    public ResponseEntity<Object> handlehsTrabajadasNoValidaException(
            hsTrabajadasNoValidaException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(JornadaNoValidaException.class)
    public ResponseEntity<Object> handleJornadaNoValidaException(
            JornadaNoValidaException ex
    ) {
        Map<String, Object> responseBody = new LinkedHashMap<>();
        responseBody.put("message", ex.getMessage());

        return new ResponseEntity<>(responseBody, HttpStatus.BAD_REQUEST);
    }

}
