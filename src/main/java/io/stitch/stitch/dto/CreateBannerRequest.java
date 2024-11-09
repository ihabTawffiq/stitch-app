package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.io.Serial;
import java.io.Serializable;


@Getter
@Setter
public class CreateBannerRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = 2668236157120037621L;
    @Id
    private Long id;

    @NotNull
    @Size(min = 3, max = 1080)
    private String imageURL;

    private String description;

    private Integer bannerOrder;

    private Long tagId;

    private Long brandId;

}

