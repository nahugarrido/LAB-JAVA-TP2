package com.neoris.turnosrotativos.repositories;

import com.neoris.turnosrotativos.entities.Concepto;
import com.neoris.turnosrotativos.entities.Empleado;
import com.neoris.turnosrotativos.entities.Jornada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface JornadaRepository extends JpaRepository<Jornada, Long> {

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Jornada j " +
            "WHERE j.empleado = :empleado " +
            "AND j.fecha = :fecha " +
            "AND j.concepto.nombre = :diaLibre")
    boolean existsDiaLibreEnFecha(@Param("empleado") Empleado empleado, @Param("fecha") LocalDate fecha, @Param("diaLibre") String diaLibre);

    @Query("SELECT CASE WHEN COUNT(j) > 0 THEN TRUE ELSE FALSE END " +
            "FROM Jornada j " +
            "WHERE j.empleado = :empleado " +
            "AND j.fecha = :fecha " +
            "AND j.concepto = :concepto")
    boolean existsJornadaMismaFechaConcepto(@Param("empleado") Empleado empleado, @Param("fecha") LocalDate fecha, @Param("concepto") Concepto concepto);

    List<Jornada> findAllByEmpleadoAndFecha(Empleado empleado, LocalDate fecha);
}
