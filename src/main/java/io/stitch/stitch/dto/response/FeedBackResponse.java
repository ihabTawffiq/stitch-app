package io.stitch.stitch.dto.response;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class FeedBackResponse implements Serializable {
    @Serial
    private static final long serialVersionUID = -4291958061301899856L;
    private long id;
    private String machineName;
    private String message;
    private String username;
    private int rate;
    private Boolean approved;

}
