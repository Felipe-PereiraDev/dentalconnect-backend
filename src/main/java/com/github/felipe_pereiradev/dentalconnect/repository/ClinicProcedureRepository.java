package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import com.github.felipe_pereiradev.dentalconnect.model.ClinicProcedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface ClinicProcedureRepository extends JpaRepository<Clinic, UUID> {

    @Query("""
       SELECT cp
       FROM ClinicProcedure cp
       WHERE cp.id = :clinicId
       AND cp.id = :procedureId
       """)
    Optional<ClinicProcedure> findByClinicIdAndProcedureId(@Param("clinicId") UUID clinicId, @Param("procedureId") UUID procedureId);
}
