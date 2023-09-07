package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.EdadMinimaNoValidaException;
import com.neoris.turnosrotativos.exceptions.EmpleadoExistenteException;
import com.neoris.turnosrotativos.exceptions.EmpleadoNoEncontradoException;
import com.neoris.turnosrotativos.repositorys.EmpleadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpAdminService implements IAdminService {
    private static final int EDAD_MINIMA = 18;

    private final EmpleadoRepository empleadoRepository;
    private final ModelMapper modelMapper;

    public ImpAdminService(EmpleadoRepository empleadoRepository, ModelMapper modelMapper) {
        this.empleadoRepository = empleadoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public EmpleadoDTO registrarEmpleado(EmpleadoSaveDTO empleadoSaveDTO) {
        verificarEdadMinimaValida(empleadoSaveDTO.getFechaNacimiento());
        verificarDocumentoUnico(empleadoSaveDTO.getNroDocumento());
        verificarEmailUnico(empleadoSaveDTO.getEmail());

        /// Creacion de entidad
        Empleado nuevoEmpleado = crearEmpleado(empleadoSaveDTO);

        /// Persistir la entidad
        empleadoRepository.save(nuevoEmpleado);

        /// Retornar el dto de empleado
        return modelMapper.map(nuevoEmpleado, EmpleadoDTO.class);
    }

    @Override
    public List<EmpleadoDTO> obtenerEmpleados() {
        List<Empleado> empleadoList = empleadoRepository.findAll();
        return empleadoList.stream().map(empleado -> modelMapper.map(empleado, EmpleadoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public EmpleadoDTO obtenerEmpleado(Long empleadoId) {
        Empleado empleado = buscarEmpleado(empleadoId);
        return modelMapper.map(empleado, EmpleadoDTO.class);
    }

    @Override
    @Transactional
    public EmpleadoDTO actualizarEmpleado(EmpleadoSaveDTO empleadoSaveDTO, Long empleadoId) {
        /// Buscar empleado en la base de datos
        Empleado empleado = buscarEmpleado(empleadoId);

        /// Verificar edad minima valida
        verificarEdadMinimaValida(empleadoSaveDTO.getFechaNacimiento());

        /// En caso de que el 'nroDocumento' sea diferente se verifica que no exista otro empleado con ese 'nroDocumento'
        if(!empleadoSaveDTO.getNroDocumento().equals(empleado.getNroDocumento())) {
            verificarDocumentoUnico(empleadoSaveDTO.getNroDocumento());
        }

        /// En caso de que el 'email' sea diferente se verifica que no exista otro empleado con ese 'email'
        if(!empleadoSaveDTO.getEmail().equals(empleado.getEmail())) {
            verificarEmailUnico(empleadoSaveDTO.getEmail());
        }

        empleado = modelMapper.map(empleadoSaveDTO, Empleado.class);
        System.out.println(empleado);
        empleadoRepository.save(empleado);

        return modelMapper.map(empleado, EmpleadoDTO.class);
    }

    private Empleado buscarEmpleado(Long empleadoId) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleadoId);
        if(empleadoOptional.isEmpty()) {
            throw new EmpleadoNoEncontradoException("No se encontró el empleado con Id: ", empleadoId);
        } else {
            return empleadoOptional.get();
        }
    }

    private Empleado crearEmpleado(EmpleadoSaveDTO empleadoSaveDTO) {
        Empleado nuevoEmpleado = modelMapper.map(empleadoSaveDTO, Empleado.class);
        nuevoEmpleado.setFechaCreacion(LocalDate.now());
        return nuevoEmpleado;
    }

    private void verificarEdadMinimaValida(LocalDate fechaNacimiento) {
        LocalDate fechaActual = LocalDate.now();
        Period periodo = Period.between(fechaNacimiento, fechaActual);
        int edad = periodo.getYears();

       if(edad <= EDAD_MINIMA) {
           throw new EdadMinimaNoValidaException("La edad del empleado no puede ser menor a 18 años.");

       }
    }

    private void verificarDocumentoUnico(Long nroDocumento) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findByNroDocumento(nroDocumento);
        if (empleadoOptional.isPresent()) {
            throw new EmpleadoExistenteException("Ya existe un empleado con el documento ingresado.");
        }
    }

    private void verificarEmailUnico(String email) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findByEmail(email);
        if (empleadoOptional.isPresent()) {
            throw new EmpleadoExistenteException("Ya existe un empleado con el email ingresado.");
        }
    }
}
