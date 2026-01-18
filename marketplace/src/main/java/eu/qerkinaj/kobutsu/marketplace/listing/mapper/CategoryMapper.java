package eu.qerkinaj.kobutsu.marketplace.listing.mapper;

import eu.qerkinaj.kobutsu.marketplace.listing.domain.Category;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryBaseDTO;
import eu.qerkinaj.kobutsu.marketplace.listing.dto.CategoryDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "jakarta-cdi",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface CategoryMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "children", source = "children")
    CategoryDTO toCompleteDto(Category entity);

    @Mapping(target = "name", source = "name")
    CategoryBaseDTO toDto(Category entity);

    List<CategoryDTO> toDtoList(List<Category> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parent", ignore = true)
    @Mapping(target = "children", ignore = true)
    void updateEntityFromDto(CategoryDTO dto, @MappingTarget Category entity);
}