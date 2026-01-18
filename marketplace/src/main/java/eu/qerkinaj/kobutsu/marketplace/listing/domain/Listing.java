package eu.qerkinaj.kobutsu.marketplace.listing.domain;

import eu.qerkinaj.kobutsu.marketplace.listing.enums.Condition;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "listings")
public class Listing extends PanacheEntityBase {
    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @Column(name = "seller_id", columnDefinition = "uuid", nullable = false)
    public UUID sellerId;

    @Column(name = "title", nullable = false, length = 150)
    public String title;

    @Column(name = "description", columnDefinition = "text")
    public String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    public Category category;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition", nullable = false, length = 20)
    public Condition condition;

    @Embedded
    public Money price;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;

    @OneToMany(mappedBy = "listing",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    public List<ListingImage> images = new ArrayList<>();

    @OneToMany(mappedBy = "listing",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    public List<Order> orders = new ArrayList<>();

    public static List<Listing> findBySellerId(UUID sellerId) {
        return find("sellerId", sellerId).list();
    }

    public static Listing findById(UUID id) {
        return find("id", id).firstResult();
    }

    public static List<Listing> findByCategory(Category category) {
        return find("category", category).list();
    }

    public static List<Listing> findByCondition(Condition condition) {
        return find("condition", condition).list();
    }

    public static List<Listing> getAll() {
        return listAll();
    }
}
