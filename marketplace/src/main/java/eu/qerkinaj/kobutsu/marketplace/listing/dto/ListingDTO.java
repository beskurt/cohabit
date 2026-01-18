package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.UUID;

@JsonPropertyOrder({"id", "seller"})
public class ListingDTO extends ListingBaseDTO {

    private UUID id;

    private MarketplaceUserDTO seller;

    public MarketplaceUserDTO getSeller() {
        return seller;
    }

    public void setSeller(MarketplaceUserDTO seller) {
        this.seller = seller;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}
