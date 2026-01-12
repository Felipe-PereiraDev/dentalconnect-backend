package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ClinicRepository extends JpaRepository<Clinic, UUID> {

    @Query(value = """
        SELECT DISTINCT c
        FROM Clinic c
        JOIN c.dentistList d
        JOIN d.specialtyList ds
        JOIN d.scheduleList s
        WHERE ds.id = :specialtyId
          AND s.dateAt = :date
          AND c.address.state = :state
    """)
    List<Clinic> searchClinicsForPatient(
            @Param("specialtyId") Long specialtyId,
            @Param("date") LocalDate date,
            @Param("state") String state
    );

}
