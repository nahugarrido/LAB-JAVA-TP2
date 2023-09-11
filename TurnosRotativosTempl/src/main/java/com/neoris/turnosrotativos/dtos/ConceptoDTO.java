package com.neoris.turnosrotativos.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ConceptoDTO {
    private Integer id;
    private String nombre;
    private Boolean laborable;
    private Integer hsMinimo;
    private Integer hsMaximo;
}
