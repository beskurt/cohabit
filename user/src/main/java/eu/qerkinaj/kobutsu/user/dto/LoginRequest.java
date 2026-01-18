package eu.qerkinaj.kobutsu.user.dto;

public record LoginRequest(
        String email,
        String password
) {
}
