package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.EmpleadoDTO;
import com.neoris.turnosrotativos.dtos.EmpleadoSaveDTO;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.exceptions.EdadMinimaNoValidaException;
import com.neoris.turnosrotativos.exceptions.EmpleadoExistenteException;
import com.neoris.turnosrotativos.exceptions.EmpleadoNoEncontradoException;
import com.neoris.turnosrotativos.exceptions.EntidadNoEncontradaException;
import com.neoris.turnosrotativos.repositories.EmpleadoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpEmpleadoService implements IEmpleadoService {
    private static final int EDAD_MINIMA = 18;
    private final EmpleadoRepository empleadoRepository;
    private final ModelMapper modelMapper;

    public ImpEmpleadoService(EmpleadoRepository empleadoRepository, ModelMapper modelMapper) {
        this.empleadoRepository = empleadoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    @Transactional
    public EmpleadoDTO registrarEmpleado(EmpleadoSaveDTO empleadoSaveDTO) {
        verificarEdadMinimaValida(empleadoSaveDTO.getFechaNacimiento());
        verificarDocumentoUnico(empleadoSaveDTO.getNroDocumento());
        verificarEmailUnico(empleadoSaveDTO.getEmail());

        Empleado nuevoEmpleado = crearEmpleado(empleadoSaveDTO);
        empleadoRepository.save(nuevoEmpleado);

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
        Empleado encontrado = buscarEmpleado(empleadoId);
        verificarEdadMinimaValida(empleadoSaveDTO.getFechaNacimiento());

        if(!empleadoSaveDTO.getNroDocumento().equals(encontrado.getNroDocumento())) {
            verificarDocumentoUnico(empleadoSaveDTO.getNroDocumento());
        }

        if(!empleadoSaveDTO.getEmail().equals(encontrado.getEmail())) {
            verificarEmailUnico(empleadoSaveDTO.getEmail());
        }

        Empleado empleadoSave = actualizarCampos(encontrado, empleadoSaveDTO);
        empleadoRepository.save(empleadoSave);

        return modelMapper.map(encontrado, EmpleadoDTO.class);
    }

    /* Esta funcion se utiliza en jornadaService */
    @Override
    public Empleado buscarEmpleadoEntity(Long idEmpleado) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(idEmpleado);
        if(empleadoOptional.isEmpty()) {
            throw new EntidadNoEncontradaException("No existe el empleado ingresado.");
        } else {
            return empleadoOptional.get();
        }
    }

    private Empleado buscarEmpleado(Long empleadoId) {
        Optional<Empleado> empleadoOptional = empleadoRepository.findById(empleadoId);
        if(empleadoOptional.isEmpty()) {
            throw new EmpleadoNoEncontradoException("No se encontró el empleado con Id: ", empleadoId);
        } else {
            return empleadoOptional.get();
        }
    }

    private Empleado actualizarCampos(Empleado empleado, EmpleadoSaveDTO empleadoNuevo) {
        empleado.setNombre(empleadoNuevo.getNombre());
        empleado.setApellido(empleadoNuevo.getApellido());
        empleado.setEmail(empleadoNuevo.getEmail());
        empleado.setFechaIngreso(empleadoNuevo.getFechaIngreso());
        empleado.setFechaNacimiento(empleadoNuevo.getFechaNacimiento());
        empleado.setNroDocumento(empleadoNuevo.getNroDocumento());
        return empleado;
    }

    private Empleado crearEmpleado(EmpleadoSaveDTO empleadoSaveDTO) {
        Empleado nuevoEmpleado = modelMapper.map(empleadoSaveDTO, Empleado.class);
        nuevoEmpleado.setFechaCreacion(LocalDateTime.now());
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
