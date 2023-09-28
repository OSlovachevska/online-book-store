package service;

import java.util.List;

import dto.BookDto;
import dto.CreateBookRequestDto;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDto> findAll();

    BookDto getById (Long id);
}
