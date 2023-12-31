package slovachevska.onlinebookstore.service.book;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import slovachevska.onlinebookstore.dto.book.BookDtoWithoutCategoryIds;
import slovachevska.onlinebookstore.dto.book.BookResponseDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;
import slovachevska.onlinebookstore.exception.EntityNotFoundException;
import slovachevska.onlinebookstore.mapper.BookMapper;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.repository.book.BookRepository;
import slovachevska.onlinebookstore.repository.book.BookSearchParameters;
import slovachevska.onlinebookstore.repository.book.BookSpecificationBuilder;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder bookSpecificationBuilder;

    @Override
    public BookResponseDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookResponseDto> findAll(Pageable pageable) {
        return bookRepository.findAll(pageable).stream()
                .map(bookMapper::toDto)
                .toList();
    }

    @Override
    public List<BookDtoWithoutCategoryIds> findAllByCategoryId(Long categoryId, Pageable pageable) {
        return bookRepository.findAllBooksByCategoryId(categoryId, pageable).stream()
                .map(bookMapper::toDtoWithoutCategories)
                .toList();

    }

    @Override
    public BookResponseDto getById(Long id) {
        return bookRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(()
                        -> new EntityNotFoundException("Can`t get book by id: " + id));
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);

    }

    @Override
    public List<BookResponseDto> search(BookSearchParameters bookSearchParameters) {
        Specification<Book> bookSpecification = bookSpecificationBuilder
                .build(bookSearchParameters);
        return bookRepository.findAll().stream()
                .map(bookMapper::toDto)
                .toList();
    }

}

