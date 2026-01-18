package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.Listing;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.ListingOrderDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(
        componentModel = "jakarta-cdi",
        uses = {
                MoneyMapper.class,
                ListingImageMapper.class,
                CategoryMapper.class,
                OrderMapper.class,
                MarketplaceUserMapper.class
        }
)
public interface ListingMapper {

    //TODO: fick mapstruct

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "condition", source = "condition")
    @Mapping(target = "price", source = "price")
    Listing toEntity(ListingBaseDTO dto);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "seller.userId", source = "sellerId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "condition", source = "condition")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "images", source = "images")
    ListingDTO toDto(Listing listing);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "condition", source = "condition")
    @Mapping(target = "price", source = "price")
    void updateEntityFromDto(ListingBaseDTO dto, @MappingTarget Listing entity);

    List<ListingDTO> toDtoList(List<Listing> entities);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "category", source = "category")
    @Mapping(target = "condition", source = "condition")
    @Mapping(target = "price", source = "price")
    @Mapping(target = "images", source = "images")
    @Mapping(target = "orders", source = "orders")
    ListingOrderDTO toDtoComplete(Listing entity);

    List<ListingOrderDTO> toDtoCompleteList(List<Listing> entities);


}
