package io.stitch.stitch.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BannerDTO {

        private Long id;

        @Size(max = 1080)
        private String imageURL;

        private int bannerOrder;

        @Size(max = 255)
        private String description;

        private TagDTO tagDTO;
        private BrandDTO brandDTO;

}
