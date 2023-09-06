package com.neoris.turnosrotativos.exceptions;

public class EmpleadoExistenteException extends RuntimeException{
    public EmpleadoExistenteException(String message) {
        super(message);
    }
}
