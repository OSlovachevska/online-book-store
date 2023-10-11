package slovachevska.onlinebookstore.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
}
