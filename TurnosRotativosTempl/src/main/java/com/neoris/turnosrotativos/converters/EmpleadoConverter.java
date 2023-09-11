package com.neoris.turnosrotativos.converters;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class EmpleadoConverter extends AbstractConverter<Empleado, EmpleadoDTO> {

    @Override
    protected EmpleadoDTO convert(Empleado source) {
        EmpleadoDTO target = new EmpleadoDTO();
        target.setId(source.getId());
        target.setNombre(source.getNombre());
        target.setApellido(source.getApellido());
        target.setFechaNacimiento(source.getFechaNacimiento());
        target.setEmail(source.getEmail());
        target.setNroDocumento(source.getNroDocumento());
        target.setFechaIngreso(source.getFechaIngreso());
        target.setFechaCreacion(source.getFechaCreacion().toLocalDate());
        return target;
    }
}