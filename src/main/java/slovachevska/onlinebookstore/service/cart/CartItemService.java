package slovachevska.onlinebookstore.service.cart;

import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.dto.cart.CartItemUpdateRequestDto;

public interface CartItemService {

    void create(String username, CartItemCreateRequestDto requestDto);

    CartItemResponseDto getById(Long id);

    void update(Long cartItemId, CartItemUpdateRequestDto requestDto);

    void deleteById(Long id);
}
