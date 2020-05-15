package wolox.training.repositories;

import ch.qos.logback.core.pattern.parser.OptionTokenizer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@EnableJpaRepositories
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Optional<Book> findOneByAuthor(String author);

    Optional<Book> findById(Long id);

    List<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    void deleteById(Long id);

    Book deleteByIsbn(String isbn);

    List<Book> findByAuthor(String author);

    List<Book> findByPublisher(String publisher);
}
