package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;

import java.time.LocalDate;
import java.util.List;

public interface IJornadaService {
    JornadaDTO registrarJornada(JornadaSaveDTO jornadaSaveDTO);

    List<JornadaDTO> obtenerJornadasPorNroDocumentoYFecha(Long nroDocumento, LocalDate fecha);

    List<JornadaDTO> obtenerJornadasPorNroDocumento(Long nroDocumento);

    List<JornadaDTO> obtenerJornadasPorFecha(LocalDate fecha);

    List<JornadaDTO> obtenerJornadas();
}
