package eu.qerkinaj.kobutsu.user.service;

import eu.qerkinaj.kobutsu.common.Role;
import eu.qerkinaj.kobutsu.user.domain.Address;
import eu.qerkinaj.kobutsu.user.domain.User;
import eu.qerkinaj.kobutsu.user.dto.UserDTO;
import eu.qerkinaj.kobutsu.user.mapper.UserMapper;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

@ApplicationScoped
public class RegistrationService {

    private static final Logger log = LoggerFactory.getLogger(RegistrationService.class);

    @Inject
    UserMapper userMapper;

    @Transactional
    public UserDTO register(UserDTO dto) {
        String email = dto.email();

        log.debug("Registering user with email {}", email);

        if (email == null || email.isEmpty()) {
            throw new IllegalArgumentException("Invalid registration data");
        }

        User user = User.findByEmail(email);
        if (user != null) {
            throw new IllegalArgumentException("User already exists");
        }

        User newUser = new User();
        newUser.email = dto.email();
        newUser.password = BcryptUtil.bcryptHash(dto.password());
        newUser.roles = Set.of(Role.USER);
        newUser.persist();

        //TODO: Validate address
        Address address = new Address();
        address.city = dto.address().city();
        address.street = dto.address().street();
        address.postalCode = dto.address().postalCode();
        address.country = dto.address().country();
        newUser.address = address;
        address.persist();


        log.debug("Successfully registered user with email {}", email);

        return userMapper.toDto(newUser);

    }


}
