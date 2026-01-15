package com.github.felipe_pereiradev.dentalconnect.repository;

import com.github.felipe_pereiradev.dentalconnect.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ProcedureRepository extends JpaRepository<Procedure, UUID> {

    @Query(value = "SELECT COUNT(p) > 0 FROM Procedure p WHERE UPPER(TRIM(p.name)) = UPPER(TRIM(:name))")
    boolean existsByName(@Param("name") String name);

    @Query(value = "SELECT COUNT(p) > 0 FROM Procedure p WHERE UPPER(TRIM(p.name)) = UPPER(TRIM(:name)) AND p.id <> :procedureId")
    boolean existsByNameAndIdNot(@Param("name") String name, @Param("procedureId")  UUID procedureId);

    List<Procedure> findAllByActiveTrue();

    @Query(value = "SELECT p FROM Procedure p WHERE UPPER(TRIM(p.name)) LIKE UPPER(TRIM(CONCAT('%', :name, '%'))) AND p.active = true")
    List<Procedure> findAllByName(@Param("name") String name);

}
