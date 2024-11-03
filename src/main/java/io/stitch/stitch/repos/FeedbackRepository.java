package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface FeedbackRepository extends MongoRepository<Feedback, Long> {
List<Feedback> findAllByApprovedFalse();
List<Feedback> findAllByApprovedTrueAndMachineId(Long machineId);
}
