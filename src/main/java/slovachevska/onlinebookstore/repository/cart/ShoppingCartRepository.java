package slovachevska.onlinebookstore.repository.cart;

import org.springframework.data.jpa.repository.JpaRepository;
import slovachevska.onlinebookstore.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findShoppingCartByUserEmail(String email);
}
