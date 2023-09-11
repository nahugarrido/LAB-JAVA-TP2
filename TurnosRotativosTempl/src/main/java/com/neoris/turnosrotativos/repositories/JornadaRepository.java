package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {
}
