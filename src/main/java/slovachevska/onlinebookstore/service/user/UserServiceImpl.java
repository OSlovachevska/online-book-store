package slovachevska.onlinebookstore.service.user;

import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import slovachevska.onlinebookstore.dto.user.UserRegistrationRequest;
import slovachevska.onlinebookstore.dto.user.UserRegistrationResponseDto;
import slovachevska.onlinebookstore.exception.RegistrationException;
import slovachevska.onlinebookstore.mapper.UserMapper;
import slovachevska.onlinebookstore.model.RoleName;
import slovachevska.onlinebookstore.model.User;
import slovachevska.onlinebookstore.repository.user.UserRepository;
import slovachevska.onlinebookstore.service.role.RoleService;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private final String adminEmail = "admin@example.com";

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final RoleService roleService;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequest request)
            throws RegistrationException {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {

            throw new RegistrationException("Unable to complete registration");
        }

        User user = userMapper.toModel(request);
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(Set.of(roleService.findRoleByName(RoleName.USER)));
        if (request.getEmail().equals(adminEmail)) {
            user.setRoles(Set.of(roleService.findRoleByName(RoleName.ADMIN)));
        }
        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
