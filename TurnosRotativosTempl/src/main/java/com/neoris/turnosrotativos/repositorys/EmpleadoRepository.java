package com.neoris.turnosrotativos.repositorys;

import com.neoris.turnosrotativos.entities.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, Long> {

    Optional<Empleado> findByNroDocumento(Long nroDocumento);

    Optional<Empleado> findByEmail(String email);
}
