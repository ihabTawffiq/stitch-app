package io.stitch.stitch.dto.response;

import io.stitch.stitch.constants.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -1990027114756332435L;
    private Long orderId;
    private List<String> machines;
    private String fullName;
    private String phoneNumber;
    private String address;
    private Double price;
    private OrderStatus status;
}
