package slovachevska.onlinebookstore.service;

import java.util.List;
import slovachevska.onlinebookstore.dto.BookDto;
import slovachevska.onlinebookstore.dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getById(Long id);
}
