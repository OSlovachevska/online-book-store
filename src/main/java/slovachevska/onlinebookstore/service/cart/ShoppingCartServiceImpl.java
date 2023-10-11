package slovachevska.onlinebookstore.service.cart;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import slovachevska.onlinebookstore.mapper.ShoppingCartMapper;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartMapper shoppingCartMapper;

    private final ShoppingCartRepository shoppingCartRepository;

    @Override
    public ShoppingCartResponseDto getShoppingCartByUserEmail(String username) {
        return shoppingCartMapper
                .toDto(shoppingCartRepository.findShoppingCartByUserEmail(username));
    }
}
