package io.stitch.stitch.dto.response;

import lombok.Data;

@Data
public class FeedBackResponse {

    private long id;
    private String machineName;
    private String message;
    private String username;
    private int rate;
    private Boolean approved;

}
