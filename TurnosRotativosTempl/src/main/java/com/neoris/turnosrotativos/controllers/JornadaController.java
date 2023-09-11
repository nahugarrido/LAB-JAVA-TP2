package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;
import com.neoris.turnosrotativos.services.IJornadaService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/jornada")
public class JornadaController {
    private final IJornadaService iJornadaService;

    public JornadaController(IJornadaService iJornadaService) {
        this.iJornadaService = iJornadaService;
    }

    @PostMapping
    public ResponseEntity<JornadaDTO> registrarJornada(@Valid @RequestBody JornadaSaveDTO jornadaSaveDTO) {
        JornadaDTO jornadaDTO = iJornadaService.registrarJornada(jornadaSaveDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, String.format("/jornada/%d", jornadaDTO.getId()));
        return new ResponseEntity<>(jornadaDTO, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<JornadaDTO>> obtenerJornadas(
            @RequestParam(name = "nroDocumento", required = false) Long nroDocumento,
            @RequestParam(name = "fecha", required = false) String fecha) {
            if(nroDocumento != null && fecha != null) {
                return ResponseEntity.status(HttpStatus.OK).body(iJornadaService.obtenerJornadasPorNroDocumentoYFecha(nroDocumento, LocalDate.parse(fecha)));
            } else if(nroDocumento != null) {
                return ResponseEntity.status(HttpStatus.OK).body(iJornadaService.obtenerJornadasPorNroDocumento(nroDocumento));
            } else if(fecha != null) {
                return ResponseEntity.status(HttpStatus.OK).body(iJornadaService.obtenerJornadasPorFecha(LocalDate.parse(fecha)));
            } else {
                return ResponseEntity.status(HttpStatus.OK).body(iJornadaService.obtenerJornadas());
            }
    }

}
