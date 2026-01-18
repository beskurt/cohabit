package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.ListingImage;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingImageDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi")
public interface ListingImageMapper {

    ListingImageDTO toDto(ListingImage img);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "listing", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ListingImage toEntity(ListingImageDTO dto);

    List<ListingImage> toEntity(List<ListingImageDTO> listingImageDTO);
}
