package techcart_backend.mapper;

import org.springframework.stereotype.Component;
import techcart_backend.dto.OrderItemResponse;
import techcart_backend.dto.OrderResponse;
import techcart_backend.entity.Order;
import techcart_backend.entity.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }

        List<OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .collect(Collectors.toList());

        return OrderResponse.builder()
                .orderId(order.getId())
                .totalAmount(order.getTotalAmount())
                .status(order.getStatus().name())
                .paymentMethod(order.getPaymentMethod().name())
                .createdAt(order.getCreatedAt())
                .items(itemResponses)
                .build();
    }

    private OrderItemResponse toItemResponse(OrderItem item) {
        return OrderItemResponse.builder()
                .productId(item.getProduct().getId())
                .productName(item.getProduct().getName())
                .quantity(item.getQuantity())
                .unitPrice(item.getUnitPrice())
                .build();
    }
}