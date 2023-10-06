package slovachevska.onlinebookstore.service.book;

import java.util.List;
import org.springframework.data.domain.Pageable;
import slovachevska.onlinebookstore.dto.book.BookDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;
import slovachevska.onlinebookstore.repository.book.BookSearchParameters;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll(Pageable pageable);

    BookDto getById(Long id);

    void deleteById(Long id);

    List<BookDto> search(BookSearchParameters bookSearchParameters);
}
