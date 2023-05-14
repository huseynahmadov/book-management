package az.company.msbook.service;

import az.company.msbook.dto.BookDto;
import az.company.msbook.dto.CreateBookRequest;
import az.company.msbook.dto.UpdateBookRequest;
import az.company.msbook.mapper.BookMapper;
import az.company.msbook.model.Book;
import az.company.msbook.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookMapper bookMapper;

    @InjectMocks
    private BookService bookService;

    @Test
    void createBookTest() {
        //given
        var request = new CreateBookRequest();
        request.setName("Test name");
        request.setAuthor("Author");
        request.setReleaseDate(1971);

        var book = new Book();
        book.setName("Test name");
        book.setAuthor("Author");
        book.setReleaseDate(1971);

        //when
        when(bookMapper.toBook(request)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);

        //actual
        bookService.createBook(request);

        verify(bookMapper, times(1)).toBook(request);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void getAllBooks() {
        //given
        var bookDto = new BookDto();
        bookDto.setName("Test name");

        var book = new Book();
        book.setName("Test name");

        List<Book> books = List.of(book, book);
        List<BookDto> bookDtos = List.of(bookDto, bookDto);

        //when
        when(bookRepository.findAll()).thenReturn(books);
        when(bookMapper.toBookDto(book)).thenReturn(bookDto);

        //then
        List<BookDto> actual = bookService.getAllBooks();

        verify(bookRepository, times(1)).findAll();
        verify(bookMapper, times(2)).toBookDto(book);
        assertEquals(bookDtos, actual);
    }

    @Test
    void getBookById() {
        //given
        Long id = 1L;

        var book = new Book();
        book.setName("Test name");

        var expected = new BookDto();
        expected.setName("Test name");

        //when
        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookMapper.toBookDto(book)).thenReturn(expected);

        //actual
        var actual = bookService.getBookById(id);

        verify(bookRepository, times(1)).findById(id);
        verify(bookMapper, times(1)).toBookDto(book);
        assertEquals(actual, expected);
    }

    @Test
    void updateBook() {
        //given
        var book = new Book();
        book.setId(1L);
        book.setName("Test name");

        var expected = new BookDto();
        expected.setId(1L);
        expected.setName("Test name");

        var updateBookRequest = new UpdateBookRequest();
        updateBookRequest.setName("Updated name");

        //when
        when(bookRepository.findById(book.getId())).thenReturn(Optional.of(book));
        doNothing().when(bookMapper).updateBook(book, updateBookRequest);
        when(bookRepository.save(book)).thenReturn(book);

        //actual
        bookService.updateBook(book.getId(), updateBookRequest);

        verify(bookRepository, times(1)).findById(book.getId());
        verify(bookMapper, times(1)).updateBook(book, updateBookRequest);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    void deleteBookById() {
        var book = new Book();
        book.setId(1L);
        book.setName("Test name");

        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        doNothing().when(bookRepository).deleteById(bookId);

        bookService.deleteBookById(bookId);

        verify(bookRepository, times(1)).deleteById(book.getId());
    }
}