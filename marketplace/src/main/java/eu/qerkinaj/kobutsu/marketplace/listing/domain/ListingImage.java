package eu.qerkinaj.kobutsu.marketplace.listing.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "listing_images")
public class ListingImage extends PanacheEntityBase {
    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    public Listing listing;

    @Column(name = "url", nullable = false, length = 255)
    public String url;

    @Column(name = "display_order", nullable = false)
    public int displayOrder;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    public Instant createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    public Instant updatedAt;
}