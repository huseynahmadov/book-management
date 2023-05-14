package az.company.msbook;

import az.company.msbook.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestBookRepository extends JpaRepository<Book, Long> {
}
