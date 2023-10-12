package slovachevska.onlinebookstore.repository.order;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.Order;

public interface OrderRepository extends JpaRepository<Order,Long> {

    Set<Order> findAllByUserEmail(String email);
}
