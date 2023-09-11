package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exceptions.hsTrabajadasNoValidaException;
import com.neoris.turnosrotativos.repositories.JornadaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
public class ImpJornadaService implements IJornadaService {
    private final JornadaRepository jornadaRepository;
    private final IConceptoService iConceptoService;
    private final IEmpleadoService iEmpleadoService;
    private final ModelMapper modelMapper;

    public ImpJornadaService(JornadaRepository jornadaRepository, IConceptoService iConceptoService, IEmpleadoService iEmpleadoService, ModelMapper modelMapper) {
        this.jornadaRepository = jornadaRepository;
        this.iConceptoService = iConceptoService;
        this.iEmpleadoService = iEmpleadoService;
        this.modelMapper = modelMapper;
    }

    @Override
    public JornadaDTO registrarJornada(JornadaSaveDTO jornadaNuevaDTO) {
        Empleado empleado = iEmpleadoService.buscarEmpleadoEntity(jornadaNuevaDTO.getIdEmpleado());
        Concepto concepto = iConceptoService.buscarConceptoEntity(jornadaNuevaDTO.getIdConcepto());
        /* No podia hacer la validación desde el dto porque necesitaba obtener a que concepto correspondia el id antes */
        validarConceptoHsTrabajadas(jornadaNuevaDTO, concepto);

        Jornada nuevaJornada = Jornada.builder()
                .concepto(concepto)
                .empleado(empleado)
                .fecha(jornadaNuevaDTO.getFecha())
                .horasTrabajadas(jornadaNuevaDTO.getHorasTrabajadas())
                .build();

        jornadaRepository.save(nuevaJornada);
        return modelMapper.map(nuevaJornada, JornadaDTO.class);
    }

    /* No encontre otra forma de poder validar esto siendo que la otra opcion era asumir cual era el valor de cada id y no queria tampoco hacer eso */
    private void validarConceptoHsTrabajadas(JornadaSaveDTO jornada, Concepto concepto) {
        if(concepto.getNombre().equals("Turno Normal") || concepto.getNombre().equals("Turno Extra")) {
            if(jornada.getHorasTrabajadas() == null) {
                throw new hsTrabajadasNoValidaException("'hsTrabajadas' es obligatorio para el concepto ingresado.");
            }
        } else if (concepto.getNombre().equals("Dia Libre")) {
            if(jornada.getHorasTrabajadas() != null) {
                throw new hsTrabajadasNoValidaException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'.");
            }
        }
    }


}
