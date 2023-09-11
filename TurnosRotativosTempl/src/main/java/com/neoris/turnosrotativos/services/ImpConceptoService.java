package com.neoris.turnosrotativos.services;

import com.neoris.turnosrotativos.dtos.ConceptoDTO;
import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.exceptions.NoEncontradoException;
import com.neoris.turnosrotativos.repositories.ConceptoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ImpConceptoService implements IConceptoService {
    private final ConceptoRepository conceptoRepository;
    private final ModelMapper modelMapper;

    public ImpConceptoService(ConceptoRepository conceptoRepository, ModelMapper modelMapper) {
        this.conceptoRepository = conceptoRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ConceptoDTO> obtenerConceptos() {
        List<Concepto> conceptoList = conceptoRepository.findAll();
        return conceptoList.stream().map(concepto -> modelMapper.map(concepto, ConceptoDTO.class)).collect(Collectors.toList());
    }

    /* Esta funcion se utiliza en jornadaService */
    @Override
    public Concepto buscarConceptoEntity(Integer conceptoId) {
        Optional<Concepto> conceptoOptional = conceptoRepository.findById(conceptoId);
        if(conceptoOptional.isEmpty()) {
            throw new NoEncontradoException("No existe el concepto ingresado.");
        } else {
            return conceptoOptional.get();
        }
    }

}
