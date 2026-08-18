package techcart_backend.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import techcart_backend.dto.OrderCreateRequest;
import techcart_backend.dto.OrderResponse;
import techcart_backend.entity.Order;
import techcart_backend.entity.OrderItem;
import techcart_backend.entity.Product;
import techcart_backend.mapper.OrderMapper;
import techcart_backend.service.OrderService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final OrderMapper orderMapper;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        List<OrderItem> items = request.getItems().stream().map(itemReq -> {
            Product product = Product.builder().id(itemReq.getProductId()).build();
            return OrderItem.builder()
                    .product(product)
                    .quantity(itemReq.getQuantity())
                    .build();
        }).collect(Collectors.toList());

        Order.PaymentMethod paymentMethod = Order.PaymentMethod.valueOf(request.getPaymentMethod().toUpperCase());

        Order createdOrder = orderService.createOrder(request.getUserId(), items, paymentMethod);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(orderMapper.toResponse(createdOrder));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByUserId(@PathVariable Long userId) {
        List<Order> orders = orderService.getOrdersByUserId(userId);
        List<OrderResponse> response = orders.stream()
                .map(orderMapper::toResponse)
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long id) {
        Order order = orderService.getOrderById(id);
        return ResponseEntity.ok(orderMapper.toResponse(order));
    }
}