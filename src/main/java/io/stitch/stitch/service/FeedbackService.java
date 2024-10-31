package io.stitch.stitch.service;

import io.stitch.stitch.dto.requets.SendFeedbackRequest;
import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.repos.FeedbackRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final MachineRepository machineRepository;
    private final PrimarySequenceService primarySequenceService;


    public FeedbackService(FeedbackRepository feedbackRepository, MachineRepository machineRepository, PrimarySequenceService primarySequenceService) {
        this.feedbackRepository = feedbackRepository;
        this.machineRepository = machineRepository;
        this.primarySequenceService = primarySequenceService;
    }

    public Long createFeedback(SendFeedbackRequest sendFeedbackRequest) {
        Feedback feedback = new Feedback();
        feedback.setId(primarySequenceService.getNextValue());
        feedback.setMessage(sendFeedbackRequest.getMessage());
        feedback.setUsername(sendFeedbackRequest.getUsername());
        feedback.setRate(sendFeedbackRequest.getRate());
        feedback.setMachine(machineRepository.findById(sendFeedbackRequest.getMachineId()).orElseThrow(NotFoundException::new));
        feedback.setApproved(false);
        Feedback savedFeedback = feedbackRepository.save(feedback);
        return savedFeedback.getId();
    }
}
