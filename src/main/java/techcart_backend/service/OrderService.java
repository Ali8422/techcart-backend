package techcart_backend.service;

import techcart_backend.entity.Order;
import techcart_backend.entity.OrderItem;

import java.util.List;

public interface OrderService {
    Order createOrder(Long userId, List<OrderItem> items, Order.PaymentMethod paymentMethod);
    List<Order> getOrdersByUserId(Long userId);
    Order getOrderById(Long id);
}