package slovachevska.onlinebookstore.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.Role;
import slovachevska.onlinebookstore.model.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(RoleName name);
}
