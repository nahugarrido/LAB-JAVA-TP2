package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;

public interface IAdminService {

    EmpleadoDTO registrarEmpleado(EmpleadoSaveDTO empleadoSaveDTO);
}
