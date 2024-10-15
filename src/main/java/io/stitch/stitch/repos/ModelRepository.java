package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Model;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface ModelRepository extends MongoRepository<Model, Long> {
}
