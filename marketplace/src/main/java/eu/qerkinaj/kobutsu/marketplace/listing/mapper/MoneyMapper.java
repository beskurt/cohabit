package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.currency.MoneyDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.domain.Money;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "jakarta-cdi")
public interface MoneyMapper {

    MoneyMapper INSTANCE = Mappers.getMapper(MoneyMapper.class);

    @Mapping(target = "amount", source = "price")
    MoneyDTO toDto(Money m);

    @InheritInverseConfiguration
    Money toEntity(MoneyDTO dto);


}
