package com.neoris.turnosrotativos.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
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
