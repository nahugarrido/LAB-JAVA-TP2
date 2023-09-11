package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;
import com.neoris.turnosrotativos.services.IJornadaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/jornada")
public class JornadaController {
    private final IJornadaService iJornadaService;

    public JornadaController(IJornadaService iJornadaService) {
        this.iJornadaService = iJornadaService;
    }

    @PostMapping
    public ResponseEntity<JornadaDTO> registrarJornada(@Valid @RequestBody JornadaSaveDTO jornadaSaveDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(iJornadaService.registrarJornada(jornadaSaveDTO));
    }

}
