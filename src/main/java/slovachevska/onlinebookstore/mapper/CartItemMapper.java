package slovachevska.onlinebookstore.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import slovachevska.onlinebookstore.config.MapperConfig;
import slovachevska.onlinebookstore.dto.cart.CartItemCreateRequestDto;
import slovachevska.onlinebookstore.dto.cart.CartItemResponseDto;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.model.CartItem;

@Mapper(config = MapperConfig.class)
public interface CartItemMapper {
    @Mapping(target = "bookId", source = "cartItem.book.id")
    @Mapping(target = "bookTitle", source = "cartItem.book.title")
    CartItemResponseDto toDto(CartItem cartItem);

    CartItem toModel(CartItemCreateRequestDto cartItemCreateRequestDto);

    @AfterMapping
    default void setBookForCreating(@MappingTarget CartItem cartItem,
                                    CartItemCreateRequestDto cartItemCreateRequestDto) {
        Book book = new Book();
        book.setId(cartItemCreateRequestDto.getBookId());
        cartItem.setBook(book);
    }
}
