package slovachevska.onlinebookstore.service.cart;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.dto.cart.CartItemUpdateRequestDto;
import slovachevska.onlinebookstore.mapper.CartItemMapper;
import slovachevska.onlinebookstore.model.CartItem;
import slovachevska.onlinebookstore.model.ShoppingCart;
import slovachevska.onlinebookstore.repository.book.BookRepository;
import slovachevska.onlinebookstore.repository.cart.CartItemRepository;
import slovachevska.onlinebookstore.repository.cart.ShoppingCartRepository;

@RequiredArgsConstructor
@Service
public class CartItemServiceImpl implements CartItemService {

    private final CartItemMapper cartItemMapper;

    private final CartItemRepository cartItemRepository;

    private final ShoppingCartRepository shoppingCartRepository;

    private final BookRepository bookRepository;

    @Override
    public void create(String username, CartItemCreateRequestDto requestDto) {
        Optional<CartItem> cartItemOptional = isBookPresentInShoppingCart(username, requestDto);
        if (cartItemOptional.isEmpty()) {
            CartItem cartItem = cartItemMapper.toModel(requestDto);
            cartItem.setBook(bookRepository.getReferenceById(cartItem.getBook().getId()));
            ShoppingCart shoppingCart =
                    shoppingCartRepository.findShoppingCartByUserEmail(username);
            cartItem.setShoppingCart(shoppingCart);
            CartItem savedCartItem = cartItemRepository.save(cartItem);
            shoppingCart.getCartItems().add(savedCartItem);
            shoppingCartRepository.save(shoppingCart);
        } else {
            updateQuantityIfBookIsPresentInShoppingCart(cartItemOptional.get(), requestDto);
        }
    }

    @Override
    public CartItemResponseDto getById(Long id) {
        return cartItemMapper.toDto(cartItemRepository.getReferenceById(id));
    }

    @Override
    public void update(Long cartItemId, CartItemUpdateRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.getReferenceById(cartItemId);
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

    }

    @Override
    public void deleteById(Long id) {
        cartItemRepository.deleteById(id);

    }

    private void updateQuantityIfBookIsPresentInShoppingCart(CartItem cartItem,
                                                             CartItemCreateRequestDto requestDto) {
        int totalQuantity = cartItem.getQuantity() + requestDto.getQuantity();
        cartItem.setQuantity(totalQuantity);
        cartItemRepository.save(cartItem);
    }

    private Optional<CartItem> isBookPresentInShoppingCart(String username,
                                                           CartItemCreateRequestDto requestDto) {
        return shoppingCartRepository.findShoppingCartByUserEmail(username).getCartItems().stream()
                .filter(cartItem -> cartItem.getBook().getId().equals(requestDto.getBookId()))
                .findFirst();
    }
}
