package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.ConceptoDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;

import java.util.List;

public interface IConceptoService {
    List<ConceptoDTO> obtenerConceptos();

    Concepto buscarConceptoEntity(Integer conceptoId);
}
