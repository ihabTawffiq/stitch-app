package io.stitch.stitch.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
public class OrderItem {
    @DocumentReference(lazy = true)
    private Machine machine;
    private int quantity;
}
