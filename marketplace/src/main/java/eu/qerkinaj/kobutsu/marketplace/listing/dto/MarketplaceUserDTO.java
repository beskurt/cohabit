package eu.qerkinaj.kobutsu.marketplace.listing.dto;

import java.util.UUID;

public record MarketplaceUserDTO(
        UUID userId,
        String email
) {
}
