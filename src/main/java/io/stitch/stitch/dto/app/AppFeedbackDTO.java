package io.stitch.stitch.dto.app;

import lombok.Data;

@Data
public class AppFeedbackDTO {

    private Long id;

    private String username;

    private int rate;

    private String message;
}
