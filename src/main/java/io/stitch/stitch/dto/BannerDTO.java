package io.stitch.stitch.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class BannerDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2813286534147099477L;

    private Long id;

    @Size(max = 1080)
    private String imageURL;

    private int bannerOrder;

    @Size(max = 255)
    private String description;

    private TagDTO tagDTO;
    private BrandDTO brandDTO;

}
