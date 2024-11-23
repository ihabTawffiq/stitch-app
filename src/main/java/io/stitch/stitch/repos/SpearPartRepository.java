package io.stitch.stitch.repos;

import io.stitch.stitch.entity.SpearPart;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface SpearPartRepository extends MongoRepository<SpearPart, Long> {
}
