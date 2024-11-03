package io.stitch.stitch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class StitchApplication {

    public static void main(final String[] args) {
        SpringApplication.run(StitchApplication.class, args);
    }

}
