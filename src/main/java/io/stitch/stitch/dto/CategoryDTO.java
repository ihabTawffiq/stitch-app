package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class CategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 4643112658215427531L;
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

}
