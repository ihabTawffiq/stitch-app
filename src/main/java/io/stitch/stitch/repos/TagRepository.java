package io.stitch.stitch.repos;

import io.stitch.stitch.domain.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TagRepository extends MongoRepository<Tag, Long> {
}
