package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

@Document
@Getter
@Setter
public class BannerDTO {

    @Id
    private Long id;

    @NotNull
    @Size(max = 1080)
    private String imageURL;

    private String description;

    private int bannerOrder;

    @DocumentReference(lazy = true)
    private TagDTO tagDTO;

    @DocumentReference(lazy = true)
    private BrandDTO brandDTO;

}

