package eu.qerkinaj.kobutsu.user.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.security.jpa.Password;
import io.quarkus.security.jpa.Roles;
import io.quarkus.security.jpa.UserDefinition;
import io.quarkus.security.jpa.Username;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
@UserDefinition
public class User extends PanacheEntityBase {

    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @Username
    @Column(name = "email", nullable = false, unique = true, length = 50)
    public String email;

    @Password
    @Column(name = "password", nullable = false)
    public String password;

    @CreationTimestamp
    @Column(name = "created_at")
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

    @Roles
    public Set<String> roles = new HashSet<>();

    @OneToOne(mappedBy = "user")
    public Address address;

    public static User findById(UUID id) {
        return find("id", id).firstResult();
    }

    public static User findByEmail(String email) {
        return find("email", email).firstResult();
    }
}
