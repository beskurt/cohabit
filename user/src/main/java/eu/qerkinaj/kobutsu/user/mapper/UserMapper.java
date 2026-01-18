package eu.qerkinaj.kobutsu.user.mapper;

import eu.qerkinaj.kobutsu.user.domain.User;
import eu.qerkinaj.kobutsu.user.dto.UserDTO;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(
        componentModel = "jakarta-cdi",
        uses = {
                AddressMapper.class
        }
)
public interface UserMapper {

    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "roles", source = "roles")
    UserDTO toDto(User user);
}