package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Table(name = "schedules")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    private UUID id;

    @Column(name = "starts_at", nullable = false)
    private LocalDateTime startsAt;

    @Column(name = "ends_at", nullable = false)
    private LocalDateTime endsAt;

    @ManyToOne
    @JoinColumn(name = "dentist_id", nullable = false, foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT))
    private Dentist dentist;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    public Schedule(Clinic clinic, Dentist dentist, LocalDateTime endsAt, LocalDateTime startsAt) {
        this.id = UuidGenerator.generate();
        this.clinic = clinic;
        this.dentist = dentist;
        this.endsAt = endsAt;
        this.startsAt = startsAt;
    }
}
