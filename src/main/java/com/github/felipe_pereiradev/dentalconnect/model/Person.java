package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.enums.PersonType;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "people", uniqueConstraints = {
        @UniqueConstraint(name = "phone_uk", columnNames = {"phone"})
})
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person extends AuditableEntity {
    @Id
    private UUID id;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "phone", nullable = false, length = 20)
    private String phone;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id", nullable = false,foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
    private User user;

    @Enumerated(EnumType.STRING)
    private PersonType personType;

    public Person(String name, String phone, PersonType personType, User user) {
        this.id = UuidGenerator.generate();
        this.name = name;
        this.phone = phone;
        this.personType = personType;
        this.user = user;
    }

}
