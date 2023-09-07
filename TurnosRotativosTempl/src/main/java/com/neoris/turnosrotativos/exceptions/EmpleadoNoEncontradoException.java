package com.neoris.turnosrotativos.exceptions;

public class EmpleadoNoEncontradoException extends RuntimeException {
    private Long empleadoId;

    public EmpleadoNoEncontradoException(String message, Long empleadoId) {
        super(message);
        this.empleadoId = empleadoId;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + empleadoId;
    }
}
