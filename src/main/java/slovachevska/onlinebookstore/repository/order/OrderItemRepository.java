package slovachevska.onlinebookstore.repository.order;

import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    Set<OrderItem> findAllByOrderId(Long orderId);
}
