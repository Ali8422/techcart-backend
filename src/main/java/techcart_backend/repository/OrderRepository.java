package techcart_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import techcart_backend.entity.Order;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    // Derived Query: Finds all orders placed by a specific user
    List<Order> findByUserId(Long userId);
}