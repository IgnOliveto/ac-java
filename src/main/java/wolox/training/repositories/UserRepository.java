package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@EnableJpaRepositories
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findOneByUsername(String username);

    Optional<User> findByUsername(String username);
}
