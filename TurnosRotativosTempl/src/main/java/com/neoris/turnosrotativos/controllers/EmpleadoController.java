package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.services.IEmpleadoService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/empleado")
public class EmpleadoController {
    private final IEmpleadoService iEmpleadoService;

    public EmpleadoController(IEmpleadoService iEmpleadoService) {
        this.iEmpleadoService = iEmpleadoService;
    }


    @PostMapping
    public ResponseEntity<EmpleadoDTO> registrarEmpleado(@Valid @RequestBody EmpleadoSaveDTO empleadoSaveDTO) {
        EmpleadoDTO empleadoDTO = iEmpleadoService.registrarEmpleado(empleadoSaveDTO);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, String.format("/empleado/%d", empleadoDTO.getId()));
        return new ResponseEntity<>(empleadoDTO, responseHeaders, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleados() {
        return ResponseEntity.status(HttpStatus.OK).body(iEmpleadoService.obtenerEmpleados());
    }

    @GetMapping(value ="/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable String empleadoId) {
        Long empleadoIdAux = Long.parseLong(empleadoId);
        EmpleadoDTO empleadoDTO = iEmpleadoService.obtenerEmpleado(empleadoIdAux);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, String.format("/empleado/%d", empleadoDTO.getId()));
        return new ResponseEntity<>(empleadoDTO, responseHeaders, HttpStatus.OK);
    }

    @PutMapping(value = "/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> actualizarEmpleado(@Valid @RequestBody EmpleadoSaveDTO empleadoSaveDTO, @PathVariable String empleadoId) {
        Long empleadoIdAux = Long.parseLong(empleadoId);
        EmpleadoDTO empleadoDTO = iEmpleadoService.actualizarEmpleado(empleadoSaveDTO, empleadoIdAux);
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set(HttpHeaders.LOCATION, String.format("/empleado/%d", empleadoDTO.getId()));
        return new ResponseEntity<>(empleadoDTO, responseHeaders, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{empleadoId}")
    public ResponseEntity<?> eliminarEmpleado(@PathVariable String empleadoId) {
        Long empleadoIdAux = Long.parseLong(empleadoId);
        iEmpleadoService.eliminarEmpleado(empleadoIdAux);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
