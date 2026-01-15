package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.dto.procedure.ProcedureUpdateDTO;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Table(name = "procedures", uniqueConstraints = {
        @UniqueConstraint(name = "procedures_name_uk", columnNames = {"name"})
})
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Procedure {

    @Id
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "active", nullable = false)
    private boolean active = true;

    public Procedure(String name, String description) {
        this.id = UuidGenerator.generate();
        this.description = description;
        this.name = name;
    }

    public void update(ProcedureUpdateDTO data) {
        if (data.name() != null && !data.name().isBlank()) {
            this.name = data.name().trim();
        }
        if (data.description() != null && !data.description().isBlank()) {
            this.description = data.description();
        }
    }
}
