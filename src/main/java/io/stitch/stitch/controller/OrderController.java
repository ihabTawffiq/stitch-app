package io.stitch.stitch.controller;

import io.stitch.stitch.constants.OrderStatus;
import io.stitch.stitch.dto.requets.OrderRequest;
import io.stitch.stitch.dto.response.OrderResponse;
import io.stitch.stitch.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(@RequestParam(name = "orderStatus") OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(orderStatus));
    }

    @PostMapping()
    public ResponseEntity<Long> createOrder(@RequestBody @Valid final OrderRequest createOrderRequest) {
        return ResponseEntity.ok(orderService.createOrder(createOrderRequest));
    }

    @GetMapping("/get-by-id")
    public ResponseEntity<OrderResponse> getOrdersById(@RequestParam(name = "id") Long id) {
        return ResponseEntity.ok(orderService.getOrderById(id));
    }
}
