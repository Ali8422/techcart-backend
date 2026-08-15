package techcart_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techcart_backend.entity.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    // Derived Query: Returns products matching a specific category ignoring case
    List<Product> findByCategoryIgnoreCase(String category);
}