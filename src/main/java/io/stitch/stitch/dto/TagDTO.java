package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class TagDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -7990311951270621192L;
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

    private Boolean display;

}
