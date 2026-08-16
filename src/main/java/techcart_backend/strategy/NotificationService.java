package techcart_backend.strategy;

public interface NotificationService {
    void sendOrderConfirmation(String recipientEmail, Long orderId);
}