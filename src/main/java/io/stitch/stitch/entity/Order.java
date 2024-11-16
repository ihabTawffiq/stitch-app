package io.stitch.stitch.entity;

import io.stitch.stitch.constants.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@Setter
public class Order {
    @Id
    private Long id;

    @NotNull
    private OrderStatus status;
    @NotNull
    private List<OrderItem> machines;

    private Double price;

    private String phoneNumber;

    private String fullName;

    private String address;

    private LocalDate createAt;

}
