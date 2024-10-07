package io.stitch.stitch.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MachineDTO {

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

    @NotNull
    private Long brand;

    @NotNull
    private Long model;

    private List<Long> tags;

}
