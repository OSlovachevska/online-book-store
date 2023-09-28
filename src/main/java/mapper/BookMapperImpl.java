package mapper;

import dto.BookDto;
import dto.CreateBookRequestDto;
import model.Book;
import org.springframework.stereotype.Component;

@Component
public class BookMapperImpl implements BookMapper {
    @Override
    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setId(book.getId());
        bookDto.setTitle(book.getTitle());
        bookDto.setAuthor(book.getAuthor());
        bookDto.setIsbn(book.getIsbn());
        bookDto.setPrice(book.getPrice());
        bookDto.setDescription(book.getDescription());
        bookDto.setCoverImage(book.getCoverImage());
        return bookDto;
    }

    @Override
    public Book toModel(CreateBookRequestDto requestDto) {
        Book book = new Book();
        book.setId(book.getId());
        book.setTitle(book.getTitle());
        book.setAuthor(book.getAuthor());
        book.setIsbn(book.getIsbn());
        book.setPrice(book.getPrice());
        book.setDescription(book.getDescription());
        book.setCoverImage(book.getCoverImage());
        return book;
    }
}
