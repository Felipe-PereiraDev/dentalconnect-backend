package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "people", uniqueConstraints = {
        @UniqueConstraint(name = "phone_uk", columnNames = {"phone"}),
        @UniqueConstraint(name = "cpf", columnNames = {"cpf"}),
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @OneToOne(mappedBy = "person", fetch = FetchType.LAZY)
    private User user;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    public Person(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

}
