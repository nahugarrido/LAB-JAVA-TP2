package com.neoris.turnosrotativos.dtos;

import lombok.Getter;
import lombok.Setter;

/**
 * Clase para dar formato a las respuestas de la api y no retornar un String sin ser un objeto json
 * De esta forma evito errores al recibir las respuestas de peticiones en el front
 */
@Getter
@Setter
public class ApiResponse {
    private String message;

    public ApiResponse(String message) {
        this.message = message;
    }
}
