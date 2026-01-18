package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.Order;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.OrderBuyerDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.OrderSellerDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "jakarta-cdi",
        uses = {
                MoneyMapper.class,
                MarketplaceUserMapper.class
        }
)
public interface OrderMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "buyer.userId", source = "buyerId")
    @Mapping(target = "purchasedAt", source = "purchasedAt")
    OrderSellerDTO toSellerDto(Order order);

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "listingId", source = "listing.id")
    @Mapping(target = "title", source = "listing.title")
    @Mapping(target = "price", source = "listing.price")
    @Mapping(target = "seller.userId", source = "listing.sellerId")
    @Mapping(target = "purchasedAt", source = "purchasedAt")
    OrderBuyerDTO toBuyerDto(Order order);

    List<OrderSellerDTO> toSellerDto(List<Order> orders);


    List<OrderBuyerDTO> toBuyerDto(List<Order> orders);

}
