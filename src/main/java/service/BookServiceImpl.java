package service;

import java.util.List;

import dto.CreateBookRequestDto;
import dto.BookDto;
import exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import mapper.BookMapper;
import model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.BookRepository;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private BookRepository bookRepository;
    private BookMapper bookMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        Book savedBook = bookRepository.save(book);
        return bookMapper.toDto(savedBook);
    }

    @Override
    public List<BookDto> findAll() {
        return bookRepository.findAll().stream().
                map(bookMapper::toDto)
                .toList();
    }

    @Override
    public BookDto getById(Long id) {
        Book book = bookRepository.getByID(id).orElseThrow(() -> new EntityNotFoundException("Can`t get book by id: " + id));
        return bookMapper.toDto(book);
    }

}

