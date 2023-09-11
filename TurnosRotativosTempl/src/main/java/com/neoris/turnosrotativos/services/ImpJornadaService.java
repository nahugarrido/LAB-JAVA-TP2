package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.JornadaDTO;
import com.neoris.turnosrotativos.dtos.JornadaSaveDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import com.neoris.turnosrotativos.exceptions.JornadaNoValidaException;
import com.neoris.turnosrotativos.exceptions.hsTrabajadasNoValidaException;
import com.neoris.turnosrotativos.repositories.JornadaRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ImpJornadaService implements IJornadaService {
    private static final String CONCEPTO_TURNO_EXTRA = "Turno Extra";
    private static final String CONCEPTO_TURNO_NORMAL = "Turno Normal";
    private static final String CONCEPTO_DIA_LIBRE = "Dia Libre";
    private static final Integer MAXIMO_HORAS_DIARIAS = 12;
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
    public JornadaDTO registrarJornada(JornadaSaveDTO nuevaJornada) {
        Empleado empleado = iEmpleadoService.buscarEmpleadoEntity(nuevaJornada.getIdEmpleado());
        Concepto concepto = iConceptoService.buscarConceptoEntity(nuevaJornada.getIdConcepto());
        validarConceptoHsTrabajadas(concepto, nuevaJornada.getHorasTrabajadas());
        validarRangoHsTrabajadas(concepto, nuevaJornada.getHorasTrabajadas());
        validarDiaDisponible(empleado, nuevaJornada.getFecha());
        validarJornadaMismaFechaConcepto(empleado, nuevaJornada.getFecha(), concepto);
        validarMaximoHsEnUnDia(empleado, nuevaJornada.getFecha(), nuevaJornada.getHorasTrabajadas());

        Jornada nuevaJornadaEntity = Jornada.builder()
                .concepto(concepto)
                .empleado(empleado)
                .fecha(nuevaJornada.getFecha())
                .horasTrabajadas(nuevaJornada.getHorasTrabajadas())
                .build();

        jornadaRepository.save(nuevaJornadaEntity);
        return modelMapper.map(nuevaJornadaEntity, JornadaDTO.class);
    }

    /* No encontre otra forma de poder validar esto siendo que la otra opcion era asumir cual era el valor de cada id y no queria hacer eso */
    private void validarConceptoHsTrabajadas(Concepto concepto, Integer horasTrabajadas) {
        if(concepto.getNombre().equals(CONCEPTO_TURNO_NORMAL) || concepto.getNombre().equals(CONCEPTO_TURNO_EXTRA)) {
            if(horasTrabajadas == null) {
                throw new hsTrabajadasNoValidaException("'hsTrabajadas' es obligatorio para el concepto ingresado.");
            }
        } else if (concepto.getNombre().equals(CONCEPTO_DIA_LIBRE)) {
            if(horasTrabajadas != null) {
                throw new hsTrabajadasNoValidaException("El concepto ingresado no requiere el ingreso de 'hsTrabajadas'.");
            }
        }
    }

    private void validarRangoHsTrabajadas(Concepto concepto, Integer horasTrabajadas) {
        if(concepto.getNombre().equals(CONCEPTO_TURNO_NORMAL) || concepto.getNombre().equals(CONCEPTO_TURNO_EXTRA)) {
            if(horasTrabajadas > concepto.getHsMaximo() || horasTrabajadas < concepto.getHsMinimo()) {
                String error = "El rango de horas que se puede cargar para este concepto es de " +
                        concepto.getHsMinimo() +
                        " - " +
                        concepto.getHsMaximo();

                throw new JornadaNoValidaException(error);
            }
        }
    }

    private void validarDiaDisponible(Empleado empleado, LocalDate fecha) {
        if(jornadaRepository.existsDiaLibreEnFecha(empleado, fecha, CONCEPTO_DIA_LIBRE)) {
            throw new JornadaNoValidaException("El empleado ingresado cuenta con un día libre en esa fecha.");
        }
    }

    private void validarJornadaMismaFechaConcepto(Empleado empleado, LocalDate fecha, Concepto concepto) {
        if(jornadaRepository.existsJornadaMismaFechaConcepto(empleado, fecha, concepto)) {
            throw new JornadaNoValidaException("“El empleado ya tiene registrado una jornada con este concepto en la fecha ingresada.");
        }
    }

    private void validarMaximoHsEnUnDia(Empleado empleado, LocalDate fecha, Integer horasTrabajadas) {
        List<Jornada> jornadas =jornadaRepository.findAllByEmpleadoAndFecha(empleado, fecha);
        int horasEnDia = horasTrabajadas;

        for(Jornada jornada : jornadas) {
            if(jornada.getConcepto().getNombre().equals(CONCEPTO_TURNO_NORMAL) || jornada.getConcepto().getNombre().equals(CONCEPTO_TURNO_EXTRA)) {
                horasEnDia += jornada.getHorasTrabajadas();
            }
        }

        if(horasEnDia > MAXIMO_HORAS_DIARIAS) {
            throw new JornadaNoValidaException("El empleado no puede cargar más de 12 horas trabajadas en un día.");
        }
    }


}
