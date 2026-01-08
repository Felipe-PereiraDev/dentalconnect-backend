package com.github.felipe_pereiradev.dentalconnect.model;


import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "Employees")
@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "employee_fk", value = ConstraintMode.CONSTRAINT))
public class Employee extends Person {

    @Column(name = "job_title", nullable = false)
    private String jobTitle;

    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false, foreignKey = @ForeignKey(name = "clinic_fk", value = ConstraintMode.CONSTRAINT))
    private Clinic clinic;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Employee(String name, String phone, PersonType personType, Clinic clinic, String jobTitle, User user) {
        super(name, phone, personType, user);
        this.clinic = clinic;
        this.jobTitle = jobTitle;
    }
}
