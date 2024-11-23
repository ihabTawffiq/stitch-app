package io.stitch.stitch.repos;

import io.stitch.stitch.entity.SpearPart;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface SpearPartRepository extends MongoRepository<SpearPart, Long> {
    List<SpearPart> findByIdIn(List<Long> ids);
}
