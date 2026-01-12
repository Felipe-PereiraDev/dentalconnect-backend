package com.github.felipe_pereiradev.dentalconnect.model;


import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.exception.ConflictException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Table(name = "dentists", uniqueConstraints = {
        @UniqueConstraint(name = "license_number_uk", columnNames = {"license_number"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT))
public class Dentist extends Person {

    @Column(name = "license_number", nullable = false)
    private String licenseNumber;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false,foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinTable(
            name = "dentist_specialties",
            uniqueConstraints = @UniqueConstraint(columnNames =
                    {"dentist_id", "specialty_id"},
                    name = "dentist_specialty_uk"
            ),
            joinColumns = @JoinColumn(
                    name = "dentist_id",
                    referencedColumnName = "id",
                    table = "dentists",
                    foreignKey = @ForeignKey(name = "dentist_fk", value = ConstraintMode.CONSTRAINT)
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "specialty_id",
                    referencedColumnName = "id",
                    table = "specialties",
                    foreignKey = @ForeignKey(name = "specialty_fk", value = ConstraintMode.CONSTRAINT)
            )
    )
    private List<Specialty> specialtyList = new ArrayList<>();

    @OneToMany(mappedBy = "dentist", fetch = FetchType.LAZY)
    private List<Schedule> scheduleList = new ArrayList<>();

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Dentist(String name, String phone, PersonType personType, User user, Clinic clinic, String licenseNumber, List<Specialty> specialtyList) {
        super(name, phone, personType, user);
        this.clinic = clinic;
        this.licenseNumber = licenseNumber;
        this.specialtyList = specialtyList;
    }

    public void addSpecialty(Specialty specialty) {
        if (specialtyList.contains(specialty)) {
            throw new ConflictException("specialty already added");
        }
        this.specialtyList.add(specialty);
    }

    public void removeSpecialty(Specialty specialty) {
        if (!specialtyList.contains(specialty)) {
            throw new ConflictException("specialty not associated");
        }
        this.specialtyList.remove(specialty);
    }
}
