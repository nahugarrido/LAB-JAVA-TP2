package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;

public interface IJornadaService {
    JornadaDTO registrarJornada(JornadaSaveDTO jornadaSaveDTO);
}
