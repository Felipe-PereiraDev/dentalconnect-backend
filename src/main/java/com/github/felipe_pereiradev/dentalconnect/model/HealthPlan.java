package com.github.felipe_pereiradev.dentalconnect.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "health_plans", uniqueConstraints = @UniqueConstraint(name = "plan_name_uk", columnNames = {"plan_name"}))
@Entity
@Getter
@Setter
@NoArgsConstructor
public class HealthPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "plan_name", nullable = false)
    private String planName;
    @Column(name = "provider", nullable = false)
    private String provider;
}
