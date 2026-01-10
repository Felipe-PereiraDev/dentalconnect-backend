package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

@Table(name = "schedules")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    private UUID id;

    @Column(name = "date_at", nullable = false)
    private LocalDate dateAt;

    @Column(name = "starts_at", nullable = false)
    private LocalTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalTime endsAt;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false, foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT))
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    public Schedule(Clinic clinic, Dentist dentist, LocalDate dateAt, LocalTime startsAt, LocalTime endsAt) {
        this.id = UuidGenerator.generate();
        this.clinic = clinic;
        this.dentist = dentist;
        this.dateAt = dateAt;
        this.startsAt = startsAt;
        this.endsAt = endsAt;

    }

    public void update(LocalTime startsAt, LocalTime endsAt) {
        if (startsAt != null) {
            this.startsAt = startsAt;
        }

        if (endsAt != null) {
            this.endsAt = endsAt;
        }
    }
}
