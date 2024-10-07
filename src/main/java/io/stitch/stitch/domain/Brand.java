package io.stitch.stitch.domain;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document
@Getter
@Setter
public class Brand {

    @Id
    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

}
