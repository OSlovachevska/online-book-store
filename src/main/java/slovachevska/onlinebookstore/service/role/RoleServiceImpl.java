package slovachevska.onlinebookstore.service.role;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.model.Role;
import slovachevska.onlinebookstore.repository.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

    @Override
    public Role findRoleByName(Role.RoleName name) {
        return roleRepository.findRoleByName(name);
    }

    @Override
    public List<Role> findAll() {
        return roleRepository.findAll();
    }
}
