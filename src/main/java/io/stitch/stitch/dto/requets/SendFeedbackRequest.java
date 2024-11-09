package io.stitch.stitch.dto.requets;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class SendFeedbackRequest implements Serializable {
    @Serial
    private static final long serialVersionUID = -1362833570167819444L;
    private long machineId;
    private String message;
    private String username;
    private int rate;
}
