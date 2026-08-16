package techcart_backend.strategy;

import techcart_backend.entity.Order;

import java.math.BigDecimal;

public interface PaymentStrategy {
    boolean processPayment(BigDecimal amount, String paymentDetails);
    Order.PaymentMethod getPaymentMethod();
}