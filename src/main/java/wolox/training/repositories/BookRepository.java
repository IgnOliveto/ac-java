package wolox.training.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import wolox.training.models.Book;

@EnableJpaRepositories
@Repository
public interface BookRepository extends JpaRepository<Book, String> {
    Book findByAuthor(String author);
}