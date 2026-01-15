package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.ClinicProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ClinicProcedureRepository extends JpaRepository<ClinicProcedure, UUID> {

    @Query("""
       SELECT cp
       FROM ClinicProcedure cp
       WHERE cp.id = :clinicId
       AND cp.id = :procedureId
       """)
    Optional<ClinicProcedure> findByClinicIdAndProcedureId(@Param("clinicId") UUID clinicId, @Param("procedureId") UUID procedureId);

    List<ClinicProcedure> findAllByActiveTrue();

    boolean existsByClinicIdAndProcedureId(@Param("clinicId") UUID clinicId, @Param("procedureId") UUID procedureId);
}
