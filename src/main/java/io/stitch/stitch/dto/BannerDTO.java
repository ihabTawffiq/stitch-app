package io.stitch.stitch.dto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class BannerDTO {


        private Long id;

        @NotNull
        @Size(max = 1080)
        private String imageURL;


        private int bannerOrder;

        @NotNull
        @Size(max = 255)
        private String description;

}
