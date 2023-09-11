package com.neoris.turnosrotativos.exceptions;

import lombok.Getter;

@Getter
public class EmpleadoNoEncontradoException extends RuntimeException {
    private final Long empleadoId;

    public EmpleadoNoEncontradoException(String message, Long empleadoId) {
        super(message);
        this.empleadoId = empleadoId;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + this.getEmpleadoId();
    }
}
