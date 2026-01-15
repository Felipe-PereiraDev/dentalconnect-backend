package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.dto.clinicprocedure.ClinicProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Table(name = "clinic_procedures")
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ClinicProcedure {

    @Id
    @EqualsAndHashCode.Include
    private UUID id;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "duration_in_minutes", nullable = false)
    private long durationInMinutes;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "procedure_id", nullable = false, foreignKey = @ForeignKey(name = "procedure_fk", value = ConstraintMode.CONSTRAINT))
    private Procedure procedure;

    @OneToMany(mappedBy = "clinicProcedure", fetch = FetchType.LAZY, orphanRemoval = true, cascade = CascadeType.ALL)
    private List<DentistClinicProcedure> dentistClinicProcedureList = new ArrayList<>();

    public ClinicProcedure(long durationInMinutes, BigDecimal price, Clinic clinic, Procedure procedure) {
        this.id = UuidGenerator.generate();
        this.clinic = clinic;
        this.durationInMinutes = durationInMinutes;
        this.price = price;
        this.procedure = procedure;
    }

    public void update(ClinicProcedureUpdateDTO data) {
        if (data.duration() != null) {
            this.durationInMinutes = data.duration();
        }
        if (data.price() != null) {
            this.price = data.price();
        }
    }
}
