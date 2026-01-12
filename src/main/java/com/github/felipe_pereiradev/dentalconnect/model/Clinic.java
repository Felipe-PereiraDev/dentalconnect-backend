package com.github.felipe_pereiradev.dentalconnect.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Table(name = "clinics", uniqueConstraints = {
        @UniqueConstraint(name = "cnpj_uk", columnNames = {"cnpj"}),
        @UniqueConstraint(name = "phone_uk", columnNames = {"phone"}),
        @UniqueConstraint(name = "clinics_address_uk", columnNames = {"address_id"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Clinic {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "cnpj", nullable = false)
    private String cnpj;

    @Column(name = "phone", nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "address_fk", value = ConstraintMode.CONSTRAINT))
    private Address address;

    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
    private List<Dentist> dentistList = new ArrayList<>();

    @OneToMany(mappedBy = "clinic", fetch = FetchType.LAZY)
    private List<Employee> employeeList = new ArrayList<>();

    public Clinic(Address address, String cnpj, String name, String phone) {
        this.id = UuidGenerator.generate();
        this.address = address;
        this.cnpj = cnpj;
        this.name = name;
        this.phone = phone;
    }
}
