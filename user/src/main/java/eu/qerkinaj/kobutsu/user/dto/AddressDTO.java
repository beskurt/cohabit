package eu.qerkinaj.kobutsu.user.dto;

public record AddressDTO(
        String street,
        String city,
        String postalCode,
        String country
) {
}
