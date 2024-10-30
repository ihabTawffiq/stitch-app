package io.stitch.stitch.entity;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
@Document
@Getter
@Setter
public class Feedback {
    @Id
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
