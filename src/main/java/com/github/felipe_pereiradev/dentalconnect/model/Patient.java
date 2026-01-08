package com.github.felipe_pereiradev.dentalconnect.model;


import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "patients", uniqueConstraints = {
        @UniqueConstraint(name = "specialty_name_uk", columnNames = {"name"}),
        @UniqueConstraint(name = "patients_address_uk", columnNames = {"address_id"}),
        @UniqueConstraint(name = "patients_cpf_uk", columnNames = {"cpf"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
@PrimaryKeyJoinColumn(name = "id", foreignKey = @ForeignKey(name = "patient_fk", value = ConstraintMode.CONSTRAINT))
public class Patient extends Person {

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @OneToOne
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "address_fk", value = ConstraintMode.CONSTRAINT))
    private Address address;

    public Patient(String name, String phone, PersonType personType, User user, Address address, String cpf) {
        super(name, phone, personType, user);
        this.address = address;
        this.cpf = cpf;
    }
}
