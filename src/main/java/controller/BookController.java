package controller;

import dto.BookDto;
import dto.CreateBookRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import service.BookService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/books")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public List<BookDto> getAll() {
        return bookService.findAll();
    };

    @GetMapping("{/id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getById(id);
    };

    @PostMapping
    public BookDto createBook(@RequestBody CreateBookRequestDto bookDto) {
        return bookService.save(bookDto);
    };
}
