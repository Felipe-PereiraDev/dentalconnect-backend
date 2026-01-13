package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

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
          AND (
              6371 * acos(
                  cos(radians(:userLat)) *
                  cos(radians(c.address.latitude)) *
                  cos(radians(c.address.longitude) - radians(:userLon)) +
                  sin(radians(:userLat)) *
                  sin(radians(c.address.latitude))
              )
            ) <= :radiusKm
    """)
    List<Clinic> searchClinicsForPatient(
            @Param("specialtyId") Long specialtyId,
            @Param("date") LocalDate date,
            @Param("userLat") double userLat,
            @Param("userLon") double userLon,
            @Param("radiusKm") double radiusKm
    );

}
