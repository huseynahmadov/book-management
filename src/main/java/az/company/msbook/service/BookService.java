package az.company.msbook.service;

import az.company.msbook.dto.BookDto;
import az.company.msbook.dto.CreateBookRequest;
import az.company.msbook.dto.UpdateBookRequest;
import az.company.msbook.exception.BookNotFoundException;
import az.company.msbook.mapper.BookMapper;
import az.company.msbook.model.Book;
import az.company.msbook.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookService {

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public void createBook(CreateBookRequest request) {
        bookRepository.save(bookMapper.toBook(request));
        log.info("Book saved successfully ! {}", request);
    }

    public List<BookDto> getAllBooks() {
        List<BookDto> books = bookRepository.findAll()
                .stream()
                .map(bookMapper::toBookDto)
                .collect(Collectors.toList());

        if (!CollectionUtils.isEmpty(books)) {
            log.info("Books fetched successfully from DB, {}", books);
            return books;
        }
        log.info("Nothing fetched from DB {}", books);
        return books;
    }

    public BookDto getBookById(Long id) {
        Book book = fetchBookFromDB(id);
        return bookMapper.toBookDto(book);
    }

    public void updateBook(Long id, UpdateBookRequest request) {
        Book book = fetchBookFromDB(id);
        bookMapper.updateBook(book, request);
        bookRepository.save(book);
        log.info("Book updated successfully!, {}", request);
    }

    public void deleteBookById(Long id) {
        Book book = fetchBookFromDB(id);
        bookRepository.deleteById(book.getId());
        log.info("Book deleted successfully from DB, {}", id);
    }

    private Book fetchBookFromDB(Long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        }
        log.error("Book not found by id : {}", id);
        throw new BookNotFoundException("Book not found by id : " + id);
    }


}
