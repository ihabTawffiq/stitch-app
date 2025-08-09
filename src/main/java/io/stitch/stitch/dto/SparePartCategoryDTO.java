package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class SparePartCategoryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 6425970044687658540L;
    private Long id;

    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 1080)
    private String description;

    @NotNull
    @Size(max = 1080)
    private String logoURL;

    private Long mainCategoryId;

    private String mainCategoryName;

    private Boolean isHomepageCategory;

}
