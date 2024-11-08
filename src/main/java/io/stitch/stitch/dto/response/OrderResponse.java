package io.stitch.stitch.dto.response;

import io.stitch.stitch.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private List<String> machines;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Double price;
    private OrderStatus status;
}
