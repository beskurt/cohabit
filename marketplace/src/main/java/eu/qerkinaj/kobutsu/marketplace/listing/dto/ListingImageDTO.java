package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"url", "displayOrder"})
public record ListingImageDTO(
        String url,
        int displayOrder
) {
}
