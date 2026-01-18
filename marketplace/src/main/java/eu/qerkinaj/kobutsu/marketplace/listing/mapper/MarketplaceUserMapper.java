package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.listing.dto.MarketplaceUserDTO;
import eu.qerkinaj.kobutsu.marketplace.user.UserClient;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.mapstruct.Mapper;

import java.util.UUID;

@Mapper(componentModel = "jakarta-cdi")
public abstract class MarketplaceUserMapper {

    @Inject
    @RestClient
    UserClient userClient;

    @ConfigProperty(name = "feature.user.client", defaultValue = "false")
    boolean enable;

    //TODO: Migrate this to userService
    public MarketplaceUserDTO toDto(UUID userId) {
        if (userId == null) {
            return null;
        }
        return new MarketplaceUserDTO(
                userId,
                enable ? userClient.getUserEmail() : "disabled"
        );
    }
}
