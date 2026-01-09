package com.github.felipe_pereiradev.dentalconnect.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "specialties", uniqueConstraints = @UniqueConstraint(name = "specialty_name_uk", columnNames = {"name"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Specialty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String description;

    public Specialty(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
