package io.stitch.stitch.dto.requets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SpearPartRequest {
    private String imageURL;
    private String name;
    private Double price;
    private String description;
}
