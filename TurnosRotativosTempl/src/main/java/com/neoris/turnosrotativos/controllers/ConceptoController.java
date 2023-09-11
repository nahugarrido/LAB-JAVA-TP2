package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.dtos.ConceptoDTO;
import com.neoris.turnosrotativos.services.IConceptoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/concepto")
public class ConceptoController {
    private final IConceptoService iConceptoService;

    public ConceptoController(IConceptoService iConceptoService) {
        this.iConceptoService = iConceptoService;
    }

    @GetMapping
    public ResponseEntity<List<ConceptoDTO>> obtenerConceptos() {
        return ResponseEntity.status(HttpStatus.OK).body(iConceptoService.obtenerConceptos());
    }
}
