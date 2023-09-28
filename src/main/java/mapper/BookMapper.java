package mapper;

import dto.CreateBookRequestDto;
import dto.BookDto;
import model.Book;

public interface BookMapper {

    BookDto toDto (Book book);

    Book toModel(CreateBookRequestDto book);
}
