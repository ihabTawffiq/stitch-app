package io.stitch.stitch.service;

import io.stitch.stitch.dto.app.AppFeedbackDTO;
import io.stitch.stitch.dto.requets.SendFeedbackRequest;
import io.stitch.stitch.dto.response.FeedBackResponse;
import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.repos.FeedbackRepository;
import io.stitch.stitch.repos.MachineRepository;
import io.stitch.stitch.util.NotFoundException;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @CacheEvict(value = "longCache",key = "'UnapprovedFeedbacks'")
    public Long updateFeedBackStatus(Long feedbackId, Boolean status) {
        Optional<Feedback> feedback = feedbackRepository.findById(feedbackId);
        if (feedback.isEmpty()) {// what exception when it is not found
            return (long) -1;
        }
        feedback.get().setApproved(status);
        feedbackRepository.save(feedback.get());
        return feedbackId;
    }

    @CacheEvict(value = "longCache",key = "'allFeedbacks'")
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

    @Cacheable(value = "longCache", key = "'allFeedbacks'")
    public List<FeedBackResponse> getFeedbacksForDashBoard() {
        AtomicReference<List<FeedBackResponse>> feedBackResponsesList = new AtomicReference<>(new ArrayList<>());
        List<Feedback> feedbacks = feedbackRepository.findAll();
        for (Feedback feedback : feedbacks) {
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            convertFeedbackToResponse(feedBackResponse, feedback);
            feedBackResponsesList.get().add(feedBackResponse);
        }
        return feedBackResponsesList.get();
    }

    @Cacheable(value = "longCache", key = "'machineFeedbacks'")
    public List<AppFeedbackDTO> getFeedBacksForMachine(Long machineId) {
        List<Feedback> feedbackList = feedbackRepository.findAllByApprovedTrueAndMachineId(machineId);
        List<AppFeedbackDTO> feedbackDTOList = new ArrayList<>();
        for (Feedback feedback : feedbackList) {
            AppFeedbackDTO appFeedbackDTO = new AppFeedbackDTO();
            convertFeedBackToAppDTO(appFeedbackDTO, feedback);
            feedbackDTOList.add(appFeedbackDTO);
        }
        return feedbackDTOList;
    }

    @Cacheable(value = "longCache", key = "'UnapprovedFeedbacks'")
    public List<FeedBackResponse> getFeedBacksForUnapproved() {
        AtomicReference<List<FeedBackResponse>> feedBackResponsesList = new AtomicReference<>(new ArrayList<>());
        List<Feedback> feedbacks = feedbackRepository.findAllByApprovedFalse();
        for (Feedback feedback : feedbacks) {
            FeedBackResponse feedBackResponse = new FeedBackResponse();
            convertFeedbackToResponse(feedBackResponse, feedback);
            feedBackResponsesList.get().add(feedBackResponse);
        }
        return feedBackResponsesList.get();
    }

    @CacheEvict(value = "longCache",key = "#id")
    public void deleteFeedback(final Long id) {
        feedbackRepository.deleteById(id);
    }

    private void convertFeedbackToResponse(FeedBackResponse feedBackResponse, Feedback feedback) {
        feedBackResponse.setApproved(feedback.getApproved());
        feedBackResponse.setMessage(feedback.getMessage());
        feedBackResponse.setUsername(feedback.getUsername());
        feedBackResponse.setRate(feedback.getRate());
        feedBackResponse.setId(feedback.getId());
        if (Objects.nonNull(feedback.getMachine()) && Objects.nonNull(feedback.getMachine().getBrand())) {
            feedBackResponse.setMachineName(feedback.getMachine().getBrand().getName() + " " + feedback.getMachine().getModel());
        }
    }

    private void convertFeedBackToAppDTO(AppFeedbackDTO appFeedbackDTO, Feedback feedback) {
        appFeedbackDTO.setId(feedback.getId());
        appFeedbackDTO.setMessage(feedback.getMessage());
        appFeedbackDTO.setUsername(feedback.getUsername());
        appFeedbackDTO.setRate(feedback.getRate());
    }


}
