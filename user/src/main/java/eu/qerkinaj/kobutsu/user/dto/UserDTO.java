package eu.qerkinaj.kobutsu.user.dto;


import java.util.Set;
import java.util.UUID;

public record UserDTO(
        UUID id,
        String email,
        String password,
        AddressDTO address,
        Set<String> roles
) {
}
