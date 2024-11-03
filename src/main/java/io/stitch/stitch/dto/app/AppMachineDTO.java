package io.stitch.stitch.dto.app;

import io.stitch.stitch.dto.BrandDTO;
import io.stitch.stitch.dto.CategoryDTO;
import io.stitch.stitch.dto.TagDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
public class AppMachineDTO {

    private Long id;


    private String model;


    private String description;

    private Boolean stock;


    private String mainImageUrl;


    private Double finalPrice;

    private Double initialPrice;


    private BrandDTO brand;

    private List<TagDTO> tags;


    private CategoryDTO category;

    private Double rate;

}
