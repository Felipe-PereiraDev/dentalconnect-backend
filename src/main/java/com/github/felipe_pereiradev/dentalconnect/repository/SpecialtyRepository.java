package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Specialty;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {

    @Query("""
       SELECT CASE WHEN COUNT(s) > 0 THEN true ELSE false END
       FROM Specialty s
       WHERE UPPER(TRIM(s.name)) = UPPER(TRIM(:name))
       """)
    boolean existsByName(@Param("name") String name);
}
