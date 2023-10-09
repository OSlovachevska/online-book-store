package slovachevska.onlinebookstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import slovachevska.onlinebookstore.model.Role;
import slovachevska.onlinebookstore.model.RoleName;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(RoleName name);
}
