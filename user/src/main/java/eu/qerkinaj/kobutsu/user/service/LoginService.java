package eu.qerkinaj.kobutsu.user.service;

import eu.qerkinaj.kobutsu.user.domain.User;
import eu.qerkinaj.kobutsu.user.dto.LoginRequest;
import eu.qerkinaj.kobutsu.user.dto.TokenResponse;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.build.Jwt;
import jakarta.enterprise.context.ApplicationScoped;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;

@ApplicationScoped
public class LoginService {

    private static final Logger log = LoggerFactory.getLogger(LoginService.class);

    public TokenResponse login(LoginRequest loginRequest) {

        if (loginRequest.email().isBlank() || loginRequest.password().isBlank()) {
            throw new IllegalArgumentException("Invalid login response");
        }

        User user = User.findByEmail(loginRequest.email());

        log.debug("login user {}", user);

        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }

        if (!BcryptUtil.matches(loginRequest.password(), user.password)) {
            throw new IllegalArgumentException("Invalid credentials");
        }

        TokenResponse token = generateToken(user);

        log.debug("login token {}", token);

        return token;
    }

    private TokenResponse generateToken(User user) {
        return new TokenResponse(
                Jwt.issuer("http://localhost:8080")
                        .subject(user.id.toString())
                        .upn(user.email)
                        .groups(user.roles)
                        .claim("userId", user.id.toString())
                        .expiresAt(Instant.now().plus(Duration.ofHours(12)))
                        .sign()
        );

    }
}
