package slovachevska.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.testcontainers.shaded.org.apache.commons.lang3.builder.EqualsBuilder;
import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.mapper.CartItemMapper;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.model.CartItem;
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.model.User;
import slovachevska.onlinebookstore.repository.book.BookRepository;
import slovachevska.onlinebookstore.repository.cart.CartItemRepository;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;
import slovachevska.onlinebookstore.service.cart.CartItemServiceImpl;

@ExtendWith(MockitoExtension.class)
class CartItemServiceTest {

    @Mock
    private CartItemRepository cartItemRepository;

    @Mock
    private CartItemMapper cartItemMapper;

    @Mock
    private ShoppingCartRepository shoppingCartRepository;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private CartItemServiceImpl cartItemService;

    @Test
    @DisplayName("Create new cart item")
    public void create_ValidCartItem_ReturnCartItem() {
        String email = "user123@gmail.com";
        CartItem cartItem = getCartItem();
        CartItemCreateRequestDto cartItemCreateRequestDto = getCartItemCreateRequestDto();
        ShoppingCart shoppingCart = new ShoppingCart()
                .setId(1L)
                        .setUser(new User().setId(1L).setEmail(email))
                                .setCartItems(new HashSet<>());

        when(shoppingCartRepository.findShoppingCartByUserEmail(email)).thenReturn(shoppingCart);
        when(cartItemMapper.toModel(cartItemCreateRequestDto)).thenReturn(cartItem);
        when(bookRepository.getReferenceById(cartItem.getBook().getId()))
                .thenReturn(cartItem.getBook());
        when(cartItemRepository.save(cartItem)).thenReturn(cartItem.setId(1L));
        cartItemService.create(email, cartItemCreateRequestDto);

        verify(shoppingCartRepository, times(2)).findShoppingCartByUserEmail(email);
        verify(cartItemRepository, times(1)).save(cartItem);
        assertTrue(shoppingCart.getCartItems().contains(cartItem));
    }

    @Test
    @DisplayName("Get cart item by id")
    public void getCartById_ReturnValidRequestDto() {
        CartItem cartItem = getCartItem();
        CartItemCreateRequestDto cartItemCreateRequestDto = getCartItemCreateRequestDto();
        CartItemResponseDto cartItemResponseDto = getCartItemResponseDto();

        when(cartItemMapper.toDto(cartItem)).thenReturn(cartItemResponseDto);
        when(cartItemRepository.getReferenceById(1L)).thenReturn(cartItem);

        CartItemResponseDto actual = cartItemService.getById(1L);

        assertNotNull(actual);
        EqualsBuilder.reflectionEquals(cartItemResponseDto, actual);

    }

    private CartItem getCartItem() {
        return new CartItem()
                .setId(1L)
                .setShoppingCart(new ShoppingCart().setId(1L))
                .setBook(new Book().setId(1L))
                .setQuantity(30);
    }

    private CartItemResponseDto getCartItemResponseDto() {
        return new CartItemResponseDto()
                .setId(1L)
                .setBookId(1L)
                .setBookTitle("Title")
                .setQuantity(3);
    }

    private CartItemCreateRequestDto getCartItemCreateRequestDto() {
        return new CartItemCreateRequestDto()
                .setBookId(1L)
                .setQuantity(3);
    }
}
