package slovachevska.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(Role.RoleName name);
}
