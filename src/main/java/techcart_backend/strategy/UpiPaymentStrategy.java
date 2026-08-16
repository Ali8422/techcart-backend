package techcart_backend.strategy;

import org.springframework.stereotype.Component;
import techcart_backend.entity.Order;

import java.math.BigDecimal;

@Component
public class UpiPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean processPayment(BigDecimal amount, String paymentDetails) {
        // Business Logic: Process UPI transaction via gateway
        return true;
    }

    @Override
    public Order.PaymentMethod getPaymentMethod() {
        return Order.PaymentMethod.UPI;
    }
}