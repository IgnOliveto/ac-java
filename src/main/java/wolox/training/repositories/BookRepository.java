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

    Optional<Book> findOneByPublisher(String publisher);

    List<Book> findByTitle(String title);

    Optional<Book> findByIsbn(String isbn);

    void deleteById(Long id);

    List<Book> deleteByAuthor(String author);

    List<Book> deleteByPublisher(String publisher);

    Book deleteByIsbn(String isbn);
}
