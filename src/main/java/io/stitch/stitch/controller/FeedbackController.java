package io.stitch.stitch.controller;

import io.stitch.stitch.dto.requets.SendFeedbackRequest;
import io.stitch.stitch.service.FeedbackService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Long> postFeedback(@RequestBody SendFeedbackRequest sendFeedbackRequest) {
        return ResponseEntity.ok(feedbackService.createFeedback(sendFeedbackRequest));
    }
}

