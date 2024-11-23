package io.stitch.stitch.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
public class SpearPartOrderItem {
    @DocumentReference(lazy = true)
    private SpearPart spearPart;
    private int quantity;
}
