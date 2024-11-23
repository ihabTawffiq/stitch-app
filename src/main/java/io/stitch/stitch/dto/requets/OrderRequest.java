package io.stitch.stitch.dto.requets;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class OrderRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -6881099349221588957L;
    private List<Long> machines;
    private List<Long> spearParts;
    private String fullName;
    private String phoneNumber;
    private String address;
}
