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
public class SparePartMainCategory {

    @Id
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

    @NotNull
    @Size(max = 1080)
    private String logoURL;

    private Boolean isHomepageCategory;
}
