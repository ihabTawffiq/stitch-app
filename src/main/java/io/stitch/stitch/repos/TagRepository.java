package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;



public interface TagRepository extends MongoRepository<Tag, Long> {

    List<Tag> findAllByIdIn(Collection<Long> id);
    List<Tag> findAllByDisplay(Boolean display);

}
