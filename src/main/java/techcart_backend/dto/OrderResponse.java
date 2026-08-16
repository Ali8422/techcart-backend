package techcart_backend.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class OrderResponse {
    private Long orderId;
    private BigDecimal totalAmount;
    private String status;
    private String paymentMethod;
    private LocalDateTime createdAt;
    private List<OrderItemResponse> items;
}