package eu.qerkinaj.kobutsu.user.dto;

import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public record TokenResponse(
        String token
) {
}
