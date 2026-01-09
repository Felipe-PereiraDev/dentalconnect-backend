package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Dentist;
import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DentistRepository extends JpaRepository<Dentist, UUID> {
    @Query("""
       SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END
       FROM Person p
       WHERE TRIM(p.phone) = TRIM(:phone)
       """)
    boolean existsByPhone(@Param("phone") String phone);

    @Query("""
       SELECT CASE WHEN COUNT(d) > 0 THEN true ELSE false END
       FROM Dentist d
       WHERE TRIM(d.licenseNumber) = TRIM(:licenseNumber)
       """)
    boolean existsByLicenseNumber(@Param("licenseNumber") String licenseNumber);
}
