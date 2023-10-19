package slovachevska.onlinebookstore.service.book;

import java.util.List;
import org.springframework.data.domain.Pageable;
import slovachevska.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import slovachevska.onlinebookstore.dto.book.BookResponseDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;
import slovachevska.onlinebookstore.repository.book.BookSearchParameters;

public interface BookService {
    BookResponseDto save(CreateBookRequestDto requestDto);

    List<BookResponseDto> findAll(Pageable pageable);

    List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable);

    BookResponseDto getById(Long id);

    void deleteById(Long id);

    List<BookResponseDto> search(BookSearchParameters bookSearchParameters);
}
