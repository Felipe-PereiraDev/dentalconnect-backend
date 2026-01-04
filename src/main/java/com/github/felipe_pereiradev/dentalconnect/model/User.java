package com.github.felipe_pereiradev.dentalconnect.model;

import com.github.felipe_pereiradev.dentalconnect.enums.UserStatusEnum;
import com.github.felipe_pereiradev.dentalconnect.utils.UuidGenerator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(name = "email_uk", columnNames = {"email"}),
        @UniqueConstraint(name = "users_person_id_uk", columnNames = {"person_id"})
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class User implements UserDetails {

    @Id
    private UUID id;

    @Column(length = 30, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(name = "password_updated_at", nullable = false)
    private LocalDate passwordUpdatedAt;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatusEnum status;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            uniqueConstraints = @UniqueConstraint(columnNames =
                    {"user_id", "role_id"},
                    name = "role_user_uk"
            ),
            joinColumns = @JoinColumn(
                    name = "user_id",
                    referencedColumnName = "id",
                    table = "users",
                    foreignKey = @ForeignKey(name = "user_fk", value = ConstraintMode.CONSTRAINT)
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id",
                    referencedColumnName = "id",
                    table = "roles",
                    foreignKey = @ForeignKey(name = "role_fk", value = ConstraintMode.CONSTRAINT)
            )
    )
    private List<Role> roles = new ArrayList<>();

    public User(String email, String password, List<Role> roles) {
        this.id = UuidGenerator.generate();
        this.email = email;
        this.password = password;
        this.roles = roles;
        this.status = UserStatusEnum.ACTIVE;
        this.passwordUpdatedAt = LocalDate.now();
    }

    //    @OneToOne
//    @JoinColumn(name = "person_id", nullable = false,foreignKey = @ForeignKey(name = "person_fk", value = ConstraintMode.CONSTRAINT))
//    private Person person;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == UserStatusEnum.ACTIVE;
    }

}
