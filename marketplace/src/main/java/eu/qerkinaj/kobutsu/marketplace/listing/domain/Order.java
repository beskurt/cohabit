package eu.qerkinaj.kobutsu.marketplace.listing.domain;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "orders")
public class Order extends PanacheEntityBase {
    @Id
    @UuidGenerator
    @Column(name = "id", columnDefinition = "uuid", updatable = false, nullable = false)
    public UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "listing_id", nullable = false)
    public Listing listing;

    @Column(name = "buyer_id", columnDefinition = "uuid", nullable = false)
    public UUID buyerId;

    @CreationTimestamp
    @Column(name = "purchased_at", updatable = false, nullable = false)
    public Instant purchasedAt;

    public static List<Order> findOrdersByBuyerId(UUID buyerId) {
        return find("buyerId", buyerId).list();
    }
}
