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
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.model.User;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;
import slovachevska.onlinebookstore.repository.user.UserRepository;
import slovachevska.onlinebookstore.service.role.RoleService;

@RequiredArgsConstructor
@Component
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    private final ShoppingCartRepository shoppingCartRepository;

    private final RoleService roleService;

    @Override
    public UserRegistrationResponseDto register(UserRegistrationRequest requestDto)
            throws RegistrationException {
        if (userRepository.findByEmail(requestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Unable to complete registration!");
        }
        User user = userMapper.toModel(requestDto);
        user.setPassword(passwordEncoder.encode(requestDto.getPassword()));
        user.setRoles(Set.of(roleService.findRoleByName(RoleName.ROLE_USER)));
        User savedUser = userRepository.save(user);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(savedUser);
        shoppingCartRepository.save(shoppingCart);
        return userMapper.toDto(savedUser);
    }
}
