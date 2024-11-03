package io.stitch.stitch.entity;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.Set;

@Document
@Getter
@Setter
public class Machine {
    @Id
    private Long id;

    @NotNull
    @Size(max = 255)
    private String model;

    @Size(max = 1080)
    private String description;

    private Boolean stock;

    @Size(max = 1080)
    private String mainImageUrl;

    @NotNull
    private Double finalPrice;

    private Double initialPrice;

    @DocumentReference(lazy = true)
    @NotNull
    private Brand brand;

    @DocumentReference(lazy = true)
    private Set<Tag> tags;

    @DocumentReference(lazy = true)
    private Set<Feedback> feedbacks;

    @DocumentReference(lazy = true)
    @NotNull
    private Category category;

    private double rate;

}
