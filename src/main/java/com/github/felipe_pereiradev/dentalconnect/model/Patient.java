package com.github.felipe_pereiradev.dentalconnect.model;


import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "patients", uniqueConstraints = @UniqueConstraint(name = "specialty_name_uk", columnNames = {"name"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Patient {

    @ManyToOne
    private HealthPlan healthPlan;
}
