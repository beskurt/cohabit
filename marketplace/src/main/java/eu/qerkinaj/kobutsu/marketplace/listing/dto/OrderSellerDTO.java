package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import java.time.Instant;

public record OrderSellerDTO(
        MarketplaceUserDTO buyer,
        Instant purchasedAt
) {
}
