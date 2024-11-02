package io.stitch.stitch.service;

import io.stitch.stitch.dto.requets.SendFeedbackRequest;
import io.stitch.stitch.dto.response.FeedBackResponse;
import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.repos.FeedbackRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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

    public List<FeedBackResponse> getFeedbacksForDashBoard() {
        AtomicReference<List<FeedBackResponse>> feedBackResponsesList = new AtomicReference<>(new ArrayList<>());
        List<Feedback> feedbacks = feedbackRepository.findAll();
        for (Feedback feedback : feedbacks) {
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            convertFeedbackToResponse(feedBackResponse,feedback);
            feedBackResponsesList.get().add(feedBackResponse);
        }
        return feedBackResponsesList.get();
    }
    public List<FeedBackResponse> getFeedBacksForUnapproved() {
        AtomicReference<List<FeedBackResponse>> feedBackResponsesList = new AtomicReference<>(new ArrayList<>());
        List<Feedback> feedbacks = feedbackRepository.findAllByApprovedFalse();
        for (Feedback feedback : feedbacks) {
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            convertFeedbackToResponse(feedBackResponse,feedback);
            feedBackResponsesList.get().add(feedBackResponse);
        }
        return feedBackResponsesList.get();
    }

    public void deleteFeedback(final Long id) {
        feedbackRepository.deleteById(id);
    }
    private void convertFeedbackToResponse(FeedBackResponse feedBackResponse,Feedback feedback) {
        feedBackResponse.setApproved(feedback.getApproved());
        feedBackResponse.setMessage(feedback.getMessage());
        feedBackResponse.setUsername(feedback.getUsername());
        feedBackResponse.setRate(feedback.getRate());
        feedBackResponse.setId(feedback.getId());
        feedBackResponse.setMachineName(feedback.getMachine().getBrand().getName()
        + " " + feedback.getMachine().getModel());
    }

}
