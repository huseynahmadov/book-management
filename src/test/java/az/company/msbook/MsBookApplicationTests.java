package az.company.msbook;

import az.company.msbook.dto.CreateBookRequest;
import az.company.msbook.model.Book;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MsBookApplicationTests {

    private static RestTemplate restTemplate;

    @Autowired
    private TestBookRepository bookRepository;

    @LocalServerPort
    private int port;
    private String baseUrl = "http://localhost";

    @BeforeAll
    public static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    public void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/api/v1/books");
    }

    @Test
    public void testCreateBook() {
        Book book = new Book();
        book.setId(5L);
        book.setName("Test name");
        book.setAuthor("test author");
        book.setReleaseDate(1978);

        var request = new CreateBookRequest();
        request.setName("Test name");
        request.setAuthor("test author");
        request.setReleaseDate(1978);

        restTemplate.postForObject(baseUrl, request, Book.class);
        assertEquals(1, bookRepository.findAll().size());
    }

    @Test
    @Sql(statements = "INSERT INTO BOOK (id,name, author, release_date) VALUES (1,'Sherlock Holmes', 'Huseyn', 1971)",
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM BOOK WHERE id=1", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void testFindProductById() {
        Book book = restTemplate.getForObject(baseUrl + "/{id}", Book.class, 1);
        assertAll(
                () -> assertNotNull(book),
                () -> assertEquals(1, book.getId()),
                () -> assertEquals("Sherlock Holmes", book.getName())
        );

    }
}
