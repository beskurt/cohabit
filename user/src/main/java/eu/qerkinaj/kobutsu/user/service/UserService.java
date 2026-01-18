package eu.qerkinaj.kobutsu.user.service;

import eu.qerkinaj.kobutsu.user.domain.User;
import eu.qerkinaj.kobutsu.user.dto.UserDTO;
import eu.qerkinaj.kobutsu.user.mapper.UserMapper;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserMapper userMapper;

    public UserDTO getUser(UUID uuid) {
        return userMapper.toDto(User.findById(uuid));
    }

    public String getUserEmail(UUID uuid) {
        return User.findById(uuid).email;
    }
}
