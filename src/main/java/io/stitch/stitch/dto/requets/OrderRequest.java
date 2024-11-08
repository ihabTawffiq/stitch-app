package io.stitch.stitch.dto.requets;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequest {
    private List<Long> machines;
    private String fullName;
    private String phoneNumber;
    private String address;
}
