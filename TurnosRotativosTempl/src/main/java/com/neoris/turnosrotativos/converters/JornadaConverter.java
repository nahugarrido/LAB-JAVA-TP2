package com.neoris.turnosrotativos.converters;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.entities.Jornada;
import org.modelmapper.AbstractConverter;
import org.springframework.stereotype.Component;

@Component
public class JornadaConverter extends AbstractConverter<Jornada, JornadaDTO> {

    @Override
    protected JornadaDTO convert(Jornada source) {
        JornadaDTO target = new JornadaDTO();
        target.setId(source.getId());
        target.setFecha(source.getFecha());
        target.setConcepto(source.getConcepto().getNombre());
        target.setHsTrabajadas(source.getHorasTrabajadas());
        target.setNroDocumento(source.getEmpleado().getNroDocumento());
        target.setNombreCompleto(source.getEmpleado().getNombre() + " " + source.getEmpleado().getApellido());
        return target;
    }
}