package io.stitch.stitch.domain;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;


@Document
@Getter
@Setter
public class Machine {

    @Id
    private Long id;

    @Size(max = 255)
    private String title;

    @Size(max = 1080)
    private String description;

    private Boolean stock;

    @Size(max = 1080)
    private String mainImageUrl;

    private Double finalPrice;

    private Double initialPrice;

    @DocumentReference(lazy = true)
    @NotNull
    private Brand brand;

    @DocumentReference(lazy = true)
    @NotNull
    private Model model;

    @DocumentReference(lazy = true)
    private Set<Tag> tags;

}
