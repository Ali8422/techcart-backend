package techcart_backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderCreateRequest {
    private Long userId;
    private String paymentMethod;
    private List<OrderItemRequest> items;
}