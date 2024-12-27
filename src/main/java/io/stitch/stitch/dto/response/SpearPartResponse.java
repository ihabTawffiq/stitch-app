package io.stitch.stitch.dto.response;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class SpearPartResponse {
    private Long id;
    private String imageURL;
    private String name;
    private Double price;
    private String description;
}
