package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Feedback;
import org.springframework.data.mongodb.repository.MongoRepository;
public interface FeedbackRepository extends MongoRepository<Feedback, Long> {

}
