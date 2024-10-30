package io.stitch.stitch.controller;
import io.stitch.stitch.dto.FeedbackDTO;
import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;
    @PostMapping
    public ResponseEntity<Feedback> postFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        Feedback feedback = feedbackService.createFeedback(feedbackDTO);
        return ResponseEntity.ok(feedback);
        }
    }

