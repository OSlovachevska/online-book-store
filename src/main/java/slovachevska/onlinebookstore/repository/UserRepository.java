package slovachevska.onlinebookstore.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
}
