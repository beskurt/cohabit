package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import java.util.List;
import java.util.UUID;

public class ListingOrderDTO extends ListingBaseDTO {

    private UUID id;

    private List<OrderSellerDTO> orders;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public List<OrderSellerDTO> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderSellerDTO> orders) {
        this.orders = orders;
    }
}
