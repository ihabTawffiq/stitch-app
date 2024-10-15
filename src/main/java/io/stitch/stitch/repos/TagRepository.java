package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface TagRepository extends MongoRepository<Tag, Long> {
}
