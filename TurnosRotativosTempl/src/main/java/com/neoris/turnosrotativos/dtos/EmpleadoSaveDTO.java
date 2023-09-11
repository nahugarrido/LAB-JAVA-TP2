package com.neoris.turnosrotativos.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class EmpleadoSaveDTO {
    @NotNull(message = "'nroDocumento' es obligatorio.")
    private Long nroDocumento;

    @NotNull
    @NotEmpty(message = "'nombre' es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Solo se permiten letras en el campo 'nombre'")
    private String nombre;

    @NotNull
    @NotEmpty(message = "'apellido' es obligatorio.")
    @Pattern(regexp = "^[a-zA-Z ]+$", message = "Solo se permiten letras en el campo 'apellido'")
    private String apellido;

    @NotNull
    @NotEmpty(message = "'email' es obligatorio.")
    @Email(message = "El email ingresado no es correcto.")
    private String email;

    @NotNull(message = "'fechaNacimiento' es obligatorio.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de nacimiento no puede ser posterior al día de la fecha.")
    private LocalDate fechaNacimiento;

    @NotNull(message = "'fechaIngreso' es obligatorio.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past(message = "La fecha de ingreso no puede ser posterior al día de la fecha.")
    private LocalDate fechaIngreso;
}