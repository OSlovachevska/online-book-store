package slovachevska.onlinebookstore.service.role;

import java.util.List;
import slovachevska.onlinebookstore.model.Role;

public interface RoleService {
    Role save(Role role);

    Role findRoleByName(Role.RoleName roleName);

    List<Role> findAll();
}
