package slovachevska.onlinebookstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import slovachevska.onlinebookstore.dto.book.BookResponseDto;
import slovachevska.onlinebookstore.dto.book.CreateBookRequestDto;
import slovachevska.onlinebookstore.mapper.BookMapper;
import slovachevska.onlinebookstore.model.Book;
import slovachevska.onlinebookstore.model.Category;
import slovachevska.onlinebookstore.repository.book.BookRepository;
import slovachevska.onlinebookstore.service.book.BookServiceImpl;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Check if the book is in the database after saving")
    public void save_ValidBook_ReturnValidBookRequestDto() {
        Book book = getBook();
        CreateBookRequestDto bookRequestDto = getBookRequestDto();
        BookResponseDto bookResponseDto = getBookResponseDto();

        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);
        when(bookMapper.toModel(bookRequestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book.setId(1L));

        BookResponseDto actual = bookService.save(bookRequestDto);

        assertNotNull(actual);
        assertNotNull(actual.getId());
        assertEquals(bookResponseDto, actual);
    }

    @Test
    @DisplayName("Find all books")
    public void findAll_ValidBooks_ReturnOneBook() {
        Book book = getBook();
        BookResponseDto bookResponseDto = getBookResponseDto();
        Pageable pageable = PageRequest.of(0,10);
        Page<Book> page = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(page);
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        List<BookResponseDto> actual = bookService.findAll(pageable);

        assertNotNull(actual);
        assertEquals(1, actual.size());
        assertEquals(bookResponseDto, actual.get(0));
    }

    @Test
    @DisplayName("Find book by id")
    public void getById_ValidId_ReturnBookById() {
        Book book = getBook();
        BookResponseDto bookResponseDto = getBookResponseDto();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(bookResponseDto);

        BookResponseDto actual = bookService.getById(1L);

        assertNotNull(actual);
        assertEquals(bookResponseDto, actual);
    }

    @Test
    @DisplayName("Get exception if the id is not valid")
    public void getById_NotValidId_ReturnException() {
        Long id = -10L;

        when(bookRepository.findById(id)).thenThrow(new RuntimeException("error"));

        Exception exception = assertThrows(RuntimeException.class, () -> bookService.getById(id));

        assertEquals("error", exception.getMessage());
    }

    private Book getBook() {
        return new Book()
                .setAuthor("Author")
                .setIsbn("0123456789")
                .setTitle("Title")
                .setPrice(BigDecimal.valueOf(29.99))
                .setDescription("New book")
                .setCoverImage("image.jpg")
                .setCategories(Set.of(
                        new Category().setId(1L),
                        new Category().setId(2L)
                ));
    }

    private CreateBookRequestDto getBookRequestDto() {
        return new CreateBookRequestDto()
                .setAuthor("Author")
                .setIsbn("0123456789")
                .setTitle("Title")
                .setPrice(BigDecimal.valueOf(29.99))
                .setDescription("New book")
                .setCoverImage("image.jpg")
                .setCategoryIds(Set.of(1L,2L)
                );
    }

    private BookResponseDto getBookResponseDto() {
        return new BookResponseDto()
                .setId(1L)
                .setAuthor("Author")
                .setIsbn("0123456789")
                .setTitle("Title")
                .setPrice(BigDecimal.valueOf(29.99))
                .setDescription("New book")
                .setCoverImage("image.jpg")
                .setCategoryIds(Set.of(1L,2L)
                );
    }
}
