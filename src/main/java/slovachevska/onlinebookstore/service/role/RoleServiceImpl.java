package slovachevska.onlinebookstore.service.role;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.model.Role;
import slovachevska.onlinebookstore.model.RoleName;
import slovachevska.onlinebookstore.repository.role.RoleRepository;

@RequiredArgsConstructor
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role findRoleByName(RoleName name) {
        return roleRepository.findRoleByName(name);
    }
}
