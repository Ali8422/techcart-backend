package techcart_backend.strategy;

import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService implements NotificationService {

    @Override
    public void sendOrderConfirmation(String recipientEmail, Long orderId) {
        // Logic to dispatch asynchronous email notification
    }
}