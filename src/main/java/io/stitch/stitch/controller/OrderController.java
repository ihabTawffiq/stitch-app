package io.stitch.stitch.controller;

import io.stitch.stitch.constants.OrderStatus;
import io.stitch.stitch.dto.requets.OrderRequest;
import io.stitch.stitch.dto.response.OrderResponse;
import io.stitch.stitch.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/orders", produces = MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin(origins = "*", allowedHeaders = "*")
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

    @PutMapping("/update-status")
    public ResponseEntity<Long> updateOrderStatus(@RequestParam(name = "id") Long id,@RequestParam(name = "orderStatus") OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrderStatus(id,orderStatus));
    }

    @GetMapping("/get-by-phoneNumber")
    public ResponseEntity<List<OrderResponse>> getOrdersByPhoneNumber(@RequestParam(name = "phoneNumber") String phoneNumber) {
        return ResponseEntity.ok(orderService.getAllOrdersByPhoneNumber(phoneNumber));
    }

    @GetMapping("/get-by-createdDate")
    public ResponseEntity<List<OrderResponse>> getOrdersByCreatedData(@RequestParam(name = "createdDate") LocalDate createdDate) {
        return ResponseEntity.ok(orderService.getAllOrdersByCreatedDate(createdDate));
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<OrderResponse>> getAllOrders( @RequestParam(defaultValue = "0") final Integer offset,
                                                             @RequestParam(defaultValue = "3") final Integer limit) {
        return ResponseEntity.ok(orderService.getAllOrders(offset,limit));
    }
}
