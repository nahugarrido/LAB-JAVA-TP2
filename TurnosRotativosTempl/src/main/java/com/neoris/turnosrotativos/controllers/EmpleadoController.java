package com.neoris.turnosrotativos.controllers;

import com.neoris.turnosrotativos.dtos.ApiResponse;
import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.exceptions.FechaNoValidaException;
import com.neoris.turnosrotativos.services.IEmpleadoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;

@RestController
public class EmpleadoController {
    private final IEmpleadoService iEmpleadoService;

    public EmpleadoController(IEmpleadoService iEmpleadoService) {
        this.iEmpleadoService = iEmpleadoService;
    }


    @PostMapping(value = "/empleado")
    public ResponseEntity<?> registrarEmpleado(@Valid @RequestBody EmpleadoSaveDTO empleadoSaveDTO) {
        if(validarEntrada(empleadoSaveDTO)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(iEmpleadoService.registrarEmpleado(empleadoSaveDTO));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Solicitud no valida."));
        }
    }

    @GetMapping(value ="/empleado")
    public ResponseEntity<List<EmpleadoDTO>> obtenerEmpleados() {
            return ResponseEntity.status(HttpStatus.OK).body(iEmpleadoService.obtenerEmpleados());
    }

    @GetMapping(value ="/empleado/{empleadoId}")
    public ResponseEntity<EmpleadoDTO> obtenerEmpleado(@PathVariable String empleadoId) {
        Long empleadoIdAux = Long.parseLong(empleadoId);
        return ResponseEntity.status(HttpStatus.OK).body(iEmpleadoService.obtenerEmpleado(empleadoIdAux));
    }

    @PutMapping(value = "/empleado/{empleadoId}")
    public ResponseEntity<?> actualizarEmpleado(@Valid @RequestBody EmpleadoSaveDTO empleadoSaveDTO, @PathVariable String empleadoId) {
        Long empleadoIdAux = Long.parseLong(empleadoId);
        if(validarEntrada(empleadoSaveDTO)) {
            return ResponseEntity.status(HttpStatus.OK).body(iEmpleadoService.actualizarEmpleado(empleadoSaveDTO, empleadoIdAux));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Solicitud no valida."));
        }
    }




    private boolean validarEntrada(EmpleadoSaveDTO empleadoSaveDTO) {
        /// Fecha actual
        LocalDate localDate = LocalDate.now();

        /// Verificar que la fecha de ingreso no sea posterior a la fecha actual
        if(empleadoSaveDTO.getFechaIngreso().isAfter(localDate)) {
            throw new FechaNoValidaException("La fecha de ingreso no puede ser posterior al día de la fecha.");
        }

        /// Verificar que la fecha de nacimiento no sea posterior a la fecha actual
        if(empleadoSaveDTO.getFechaNacimiento().isAfter(localDate)) {
            throw new FechaNoValidaException("La fecha de nacimiento no puede ser posterior al día de la fecha.");
        }

        return true;
    }

}