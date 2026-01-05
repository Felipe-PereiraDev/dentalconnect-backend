package com.github.felipe_pereiradev.dentalconnect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "specialties", uniqueConstraints = @UniqueConstraint(name = "specialty_name_uk", columnNames = {"name"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    public Specialty(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
