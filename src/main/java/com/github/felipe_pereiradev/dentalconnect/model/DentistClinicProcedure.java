package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "dentist_clinic_procedure")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DentistClinicProcedure {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "dentist_id",foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT))
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name = "clinic_procedure_id",foreignKey = @ForeignKey(name = "clinic_procedure_fk", value = ConstraintMode.CONSTRAINT))
    private ClinicProcedure clinicProcedure;

    @Column(nullable = false)
    private boolean active = true;

    public DentistClinicProcedure(Dentist dentist, ClinicProcedure clinicProcedure) {
        this.id = UuidGenerator.generate();
        this.dentist = dentist;
        this.clinicProcedure = clinicProcedure;
    }
}