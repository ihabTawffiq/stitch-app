package io.stitch.stitch.service;

import io.stitch.stitch.entity.Feedback;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.repos.FeedbackRepository;
import io.stitch.stitch.repos.MachineRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CronJobScheduledTasks {
    private final FeedbackRepository feedbackRepository;
    private final MachineRepository machineRepository;

    public CronJobScheduledTasks(FeedbackRepository feedbackRepository, MachineRepository machineRepository) {
        this.feedbackRepository = feedbackRepository;
        this.machineRepository = machineRepository;
    }

    @Scheduled(cron = "0 0 * * * ?")
    private void scheduledMachineFeedback() {
        List<Machine> machineList = machineRepository.findAllByRateNot(-1);
        for (Machine machine : machineList) {
            List<Feedback> machineFeedbacks = feedbackRepository.findAllByApprovedTrueAndMachineId(machine.getId());
            if (!machineFeedbacks.isEmpty()) {
                double sum = machineFeedbacks.stream().mapToDouble(Feedback::getRate).sum();
                Double machineRate = sum / machineFeedbacks.size();
                if (!machineRate.equals(machine.getRate())) {
                    machine.setRate(machineRate);
                    machineRepository.save(machine);
                }
            }
        }
    }
}
