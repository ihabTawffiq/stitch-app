package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MachineDTO {

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

    @NotNull
    private Long brand;

    private List<Long> tags;

    @NotNull
    private Long category;

}
