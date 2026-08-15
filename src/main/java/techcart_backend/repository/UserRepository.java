package techcart_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techcart_backend.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Custom query method generated automatically by Spring Data JPA
    Optional<User> findByEmail(String email);

    // Checks existence directly at the SQL level (SELECT COUNT(*) > 0 ...)
    boolean existsByEmail(String email);
}