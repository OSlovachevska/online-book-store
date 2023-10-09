package slovachevska.onlinebookstore.service.role;

import slovachevska.onlinebookstore.model.Role;
import slovachevska.onlinebookstore.model.RoleName;

public interface RoleService {

    Role findRoleByName(RoleName roleName);

}
