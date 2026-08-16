package techcart_backend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import techcart_backend.entity.Order;
import techcart_backend.entity.OrderItem;
import techcart_backend.entity.Product;
import techcart_backend.entity.User;
import techcart_backend.repository.OrderRepository;
import techcart_backend.repository.ProductRepository;
import techcart_backend.repository.UserRepository;
import techcart_backend.service.OrderService;
import techcart_backend.strategy.NotificationService;
import techcart_backend.strategy.PaymentStrategy;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final List<PaymentStrategy> paymentStrategies;
    private final NotificationService notificationService;

    @Override
    @Transactional
    public Order createOrder(Long userId, List<OrderItem> items, Order.PaymentMethod paymentMethod) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (OrderItem item : items) {
            Product product = productRepository.findById(item.getProduct().getId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));

            if (product.getStockQuantity() < item.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product: " + product.getName());
            }

            // Deduct inventory stock
            product.setStockQuantity(product.getStockQuantity() - item.getQuantity());
            productRepository.save(product);

            item.setUnitPrice(product.getPrice());
            BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(itemTotal);
        }

        // Select payment strategy matching requested payment method (OCP)
        PaymentStrategy strategy = paymentStrategies.stream()
                .filter(s -> s.getPaymentMethod() == paymentMethod)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Unsupported payment method: " + paymentMethod));

        boolean paymentSuccess = strategy.processPayment(totalAmount, "Order processing for user " + userId);
        if (!paymentSuccess) {
            throw new RuntimeException("Payment processing failed");
        }

        Order order = Order.builder()
                .user(user)
                .totalAmount(totalAmount)
                .status(Order.OrderStatus.PROCESSING)
                .paymentMethod(paymentMethod)
                .build();

        for (OrderItem item : items) {
            item.setOrder(order);
        }
        order.setItems(items);

        Order savedOrder = orderRepository.save(order);

        // Send order confirmation via Notification abstraction (SRP)
        notificationService.sendOrderConfirmation(user.getEmail(), savedOrder.getId());

        return savedOrder;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    @Override
    @Transactional(readOnly = true)
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
    }
}