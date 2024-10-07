package io.stitch.stitch.model;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class TagDTO {

    private Long id;

    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

}
