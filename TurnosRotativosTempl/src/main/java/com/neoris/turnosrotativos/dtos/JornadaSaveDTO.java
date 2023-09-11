package com.neoris.turnosrotativos.dtos;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
public class JornadaSaveDTO {
    @NotNull(message = "'idEmpleado' es obligatorio.")
    private Long idEmpleado;

    @NotNull(message = "'idConcepto' es obligatorio.")
    private Integer idConcepto;

    @NotNull(message = "'fecha' es obligatorio.")
    private LocalDate fecha;

    private Integer horasTrabajadas;
}
