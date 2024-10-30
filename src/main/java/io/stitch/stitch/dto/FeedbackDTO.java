package io.stitch.stitch.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
@Getter
@Setter
public class FeedbackDTO {
    private long id;
    private long machineId;
    @NotNull
    private String message;
    @NotNull
    private String username;
    @NotNull
    private int rate;
    private Boolean approved = false;
}
