package slovachevska.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import slovachevska.onlinebookstore.mapper.ShoppingCartMapper;
import slovachevska.onlinebookstore.model.CartItem;
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.model.User;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;
import slovachevska.onlinebookstore.service.cart.ShoppingCartServiceImpl;

@ExtendWith(MockitoExtension.class)
class ShoppingCartServiceTest {

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private ShoppingCartMapper shoppingCartMapper;

    @InjectMocks
    private ShoppingCartServiceImpl shoppingCartService;

   /* @Test
    @DisplayName("Find a shopping cart by user email")
    public void getShoppingCartByUserEmail_ValidRequestDto_ReturnShoppingCart() {
        String email = "user123@gmail.com";
        ShoppingCart shoppingCart = getShoppingCart();
        ShoppingCartResponseDto shoppingCartResponseDto = getShoppingCartResponseDto();

        when(shoppingCartMapper.toDto(shoppingCart)).thenReturn(shoppingCartResponseDto);
        when(shoppingCartRepository.findShoppingCartByUserEmail(email)).thenReturn(shoppingCart);

        ShoppingCartResponseDto actual = shoppingCartService.getShoppingCartByUserEmail(email);

        assertNotNull(actual);
        assertEquals(shoppingCartResponseDto, actual);
    }
*/
 /*   private ShoppingCart getShoppingCart() {
        return new ShoppingCart()
                .setId(1L)
                .setUser(new User().setId(1L))
                .setCartItems(Set.of(
                        new CartItem().setId(1L),
                        new CartItem().setId(2L)
                ));
    }
*/
    private ShoppingCartResponseDto getShoppingCartResponseDto() {
        return new ShoppingCartResponseDto()
                .setId(1L)
                .setUserId(1L)
                .setCartItems(Set.of(
                        new CartItemResponseDto().setId(1L),
                        new CartItemResponseDto().setId(2L)
                ));
    }
}
