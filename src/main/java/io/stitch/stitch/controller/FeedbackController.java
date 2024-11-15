package io.stitch.stitch.controller;

import io.stitch.stitch.dto.app.AppFeedbackDTO;
import io.stitch.stitch.dto.requets.SendFeedbackRequest;
import io.stitch.stitch.dto.response.FeedBackResponse;
import io.stitch.stitch.service.FeedbackService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping
    public ResponseEntity<Long> postFeedback(@RequestBody SendFeedbackRequest sendFeedbackRequest) {
        return ResponseEntity.ok(feedbackService.createFeedback(sendFeedbackRequest));
    }

    @GetMapping
    public ResponseEntity<List<FeedBackResponse>> getFeedbacks() {
        return ResponseEntity.ok(feedbackService.getFeedbacksForDashBoard());
    }

    @GetMapping("/unapproved")
    public ResponseEntity<List<FeedBackResponse>> getUnapprovedFeedbacks() {
        return ResponseEntity.ok(feedbackService.getFeedBacksForUnapproved());
    }

    @DeleteMapping("/{id}")
    @ApiResponse(responseCode = "204")
    public ResponseEntity<Void> deleteFeedback(@PathVariable Long id) {
        feedbackService.deleteFeedback(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/app/by-machine-id")
    public ResponseEntity<List<AppFeedbackDTO>> getFeedBackForMachine(@RequestParam Long machineId){
        return ResponseEntity.ok(feedbackService.getFeedBacksForMachine(machineId));
    }

    @PutMapping("/update-status")
    public ResponseEntity<Long> updateFeedBackStatus(@RequestParam Long feedBackId,
                                                     @RequestParam Boolean status){
        return ResponseEntity.ok(feedbackService.updateFeedBackStatus(feedBackId,status));
    }



}

