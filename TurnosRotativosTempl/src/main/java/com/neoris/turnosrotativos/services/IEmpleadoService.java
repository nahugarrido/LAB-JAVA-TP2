package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.entities.Empleado;

import java.util.List;
import java.util.Optional;

public interface IEmpleadoService {
    EmpleadoDTO registrarEmpleado(EmpleadoSaveDTO empleadoSaveDTO);

    List<EmpleadoDTO> obtenerEmpleados();

    EmpleadoDTO obtenerEmpleado(Long empleadoIdAux);

    EmpleadoDTO actualizarEmpleado(EmpleadoSaveDTO empleadoSaveDTO, Long empleadoId);

    Empleado buscarEmpleadoEntity(Long idEmpleado);

    Optional<Empleado> buscarEmpleadoEntityPorNroDocumento(Long nroDocumento);

    void eliminarEmpleado(Long empleadoId);
}
