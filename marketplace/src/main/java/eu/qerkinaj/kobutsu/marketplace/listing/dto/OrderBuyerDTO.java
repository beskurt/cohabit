package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import eu.qerkinaj.kobutsu.marketplace.currency.MoneyDTO;

import java.time.Instant;
import java.util.UUID;

public record OrderBuyerDTO(
        UUID listingId,
        String title,
        MoneyDTO price,
        MarketplaceUserDTO seller,
        Instant purchasedAt
) {
}
