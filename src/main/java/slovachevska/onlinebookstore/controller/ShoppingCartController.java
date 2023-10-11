package slovachevska.onlinebookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemUpdateRequestDto;
import slovachevska.onlinebookstore.dto.cart.ShoppingCartResponseDto;
import slovachevska.onlinebookstore.service.cart.CartItemService;
import slovachevska.onlinebookstore.service.cart.ShoppingCartService;

@Tag(name = "Shopping cart management")
@RequiredArgsConstructor
@RestController
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    private final CartItemService cartItemService;

    @PostMapping
    @Operation(summary = "Add a book to shopping cart")
    public ShoppingCartResponseDto addBookToShoppingCart(
            Authentication authentication,
            @RequestBody @Valid CartItemCreateRequestDto requestDto) {
        cartItemService.create(authentication.name(), requestDto);
        return shoppingCartService.getShoppingCartByUserEmail(authentication.name());
    }

    @PutMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Update books quantity in shopping cart")
    public ShoppingCartResponseDto updateCartItem(
            @PathVariable Long cartItemId,
            @RequestBody @Valid CartItemUpdateRequestDto requestDto,
            Authentication authentication) {
        cartItemService.update(cartItemId, requestDto);
        return shoppingCartService.getShoppingCartByUserEmail(authentication.name());
    }

    @DeleteMapping("/cart-items/{cartItemId}")
    @Operation(summary = "Delete a book from shopping cart")
    public ShoppingCartResponseDto deleteCartItem(@PathVariable Long cartItemId,
                                                  Authentication authentication) {
        cartItemService.deleteById(cartItemId);
        return shoppingCartService.getShoppingCartByUserEmail(authentication.name());
    }

}

