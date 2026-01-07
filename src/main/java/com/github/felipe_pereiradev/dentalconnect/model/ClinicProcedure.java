package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Table(name = "clinic_procedures")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class ClinicProcedure {

    @Id
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

    public ClinicProcedure(Clinic clinic, long durationInMinutes, BigDecimal price, Procedure procedure) {
        this.id = UuidGenerator.generate();
        this.clinic = clinic;
        this.durationInMinutes = durationInMinutes;
        this.price = price;
        this.procedure = procedure;
    }
}
