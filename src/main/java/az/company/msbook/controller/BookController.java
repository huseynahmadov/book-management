package az.company.msbook.controller;

import az.company.msbook.dto.BookDto;
import az.company.msbook.dto.CreateBookRequest;
import az.company.msbook.dto.UpdateBookRequest;
import az.company.msbook.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/books")
@Validated
public class BookController {

    private final BookService bookService;

    @PostMapping
    public void createBook(@RequestBody @Valid CreateBookRequest request) {
        bookService.createBook(request);
    }

    @GetMapping
    public List<BookDto> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @PutMapping("/{id}")
    public void updateBook(@PathVariable Long id, @RequestBody @Valid UpdateBookRequest request) {
        bookService.updateBook(id, request);
    }
    @DeleteMapping("/{id}")
    public void deleteBookById(@PathVariable Long id) {
        bookService.deleteBookById(id);
    }

}
