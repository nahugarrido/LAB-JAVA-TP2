package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.EdadMinimaNoValidaException;
import com.neoris.turnosrotativos.exceptions.EmpleadoExistenteException;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpAdminService implements IAdminService {

    private final EmpleadoRepository empleadoRepository;

    private final ModelMapper modelMapper;

    public ImpAdminService(EmpleadoRepository empleadoRepository, ModelMapper modelMapper) {
        this.empleadoRepository = empleadoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmpleadoDTO registrarEmpleado(EmpleadoSaveDTO empleadoSaveDTO) {
        /// Verificar edad de empleado valida
        if(!verificarEdad(empleadoSaveDTO.getFechaNacimiento(), 18)) {
            throw new EdadMinimaNoValidaException("La edad del empleado no puede ser menor a 18 años.");
        }

        /// Verificar que el documento ingresado no existe
        Optional<Empleado> empleadoOptional1 = empleadoRepository.findByNroDocumento(empleadoSaveDTO.getNroDocumento());
        if(empleadoOptional1.isPresent()) {
            throw new EmpleadoExistenteException("Ya existe un empleado con el documento ingresado.");
        }

        /// Verificar que el mail ingresado no existe
        Optional<Empleado> empleadoOptional2 = empleadoRepository.findByEmail(empleadoSaveDTO.getEmail());
        if(empleadoOptional2.isPresent()) {
            throw new EmpleadoExistenteException("Ya existe un empleado con el email ingresado.");
        }


        /// Creacion de entidad
        Empleado nuevoEmpleado = modelMapper.map(empleadoSaveDTO, Empleado.class);
        nuevoEmpleado.setFechaCreacion(LocalDate.now());

        /// Persistencia
        empleadoRepository.save(nuevoEmpleado);

        /// Retornar el dto de empleado
        return modelMapper.map(nuevoEmpleado, EmpleadoDTO.class);
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleados() {
        List<Empleado> empleadoList = empleadoRepository.findAll();
        return empleadoList.stream().map(empleado -> modelMapper.map(empleado, EmpleadoDTO.class)).collect(Collectors.toList());
    }

    private boolean verificarEdad(LocalDate fechaNacimiento, int edadMinima) {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        int edad = periodo.getYears();

        return edad >= edadMinima;
    }
}
