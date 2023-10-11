package slovachevska.onlinebookstore.service.cart;

import slovachevska.onlinebookstore.dto.cart.ShoppingCartResponseDto;

public interface ShoppingCartService {

    ShoppingCartResponseDto getShoppingCartByUserEmail(String username);
}
