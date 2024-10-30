package io.stitch.stitch.service;
import io.stitch.stitch.dto.FeedbackDTO;
import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.repos.FeedbackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    public Feedback createFeedback(FeedbackDTO feedbackDTO) {
        Feedback feedback = new Feedback();
        feedback.setMessage(feedbackDTO.getMessage());
        feedback.setUsername(feedbackDTO.getUsername());
        feedback.setRate(feedbackDTO.getRate());
        feedback.setMachineId(feedbackDTO.getMachineId());
        feedback.setApproved(feedbackDTO.getApproved());
        return feedbackRepository.save(feedback);
    }
}
