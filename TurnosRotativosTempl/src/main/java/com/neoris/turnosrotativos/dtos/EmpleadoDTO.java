package com.neoris.turnosrotativos.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Esta clase se utiliza solo para mostrar información
 * por lo que no hacen falta verificaciones en ella
 * */
@Getter
@Setter
public class EmpleadoDTO {
    private Long id;
    private Long nroDocumento;
    private String nombre;
    private String apellido;
    private String email;
    private LocalDate fechaNacimiento;
    private LocalDate fechaIngreso;
    private LocalDate fechaCreacion;
}
