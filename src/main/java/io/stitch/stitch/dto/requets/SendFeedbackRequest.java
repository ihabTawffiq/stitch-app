package io.stitch.stitch.dto.requets;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SendFeedbackRequest {
    private long machineId;
    private String message;
    private String username;
    private int rate;
}
