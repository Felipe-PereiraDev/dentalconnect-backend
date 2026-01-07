package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.enums.AppointmentStatusEnum;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "appointments")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Appointment {

    @Id
    private UUID id;

    @Column(name = "price", nullable = false)
    private BigDecimal price = BigDecimal.ZERO;

    @Column(name = "status", nullable = false)
    private AppointmentStatusEnum status;

    @Column(name = "appointment_at", nullable = false)
    private LocalDateTime appointmentAt;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false, foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT))
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false, foreignKey = @ForeignKey(name = "patient_fk", value = ConstraintMode.CONSTRAINT))
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "clinic_procedure_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_procedure_fk", value = ConstraintMode.CONSTRAINT))
    private ClinicProcedure clinicProcedure;

    public Appointment(LocalDateTime appointmentAt, Clinic clinic, Dentist dentist, Patient patient, BigDecimal price, ClinicProcedure clinicProcedure) {
        this.id = UuidGenerator.generate();
        this.appointmentAt = appointmentAt;
        this.clinic = clinic;
        this.dentist = dentist;
        this.patient = patient;
        this.price = price;
        this.clinicProcedure = clinicProcedure;
        this.status = AppointmentStatusEnum.IN_PROGRESS;
    }
}
