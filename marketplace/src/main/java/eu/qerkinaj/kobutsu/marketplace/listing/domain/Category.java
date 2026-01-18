package eu.qerkinaj.kobutsu.marketplace.listing.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "categories")
public class Category extends PanacheEntityBase {
    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "name", nullable = false, length = 100)
    public String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    public Category parent;

    @OneToMany(mappedBy = "parent",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    public List<Category> children;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

    public static List<Category> findAllCategories() {
        return list("parent IS NULL");
    }

    public static Category findCategoryById(UUID id) {
        return findById(id);
    }

    public static Category findByName(String name) {
        return find("name", name).firstResult();
    }

    public static List<Category> findByParentId(UUID parentId) {
        return list("parent.id", parentId);
    }
}
