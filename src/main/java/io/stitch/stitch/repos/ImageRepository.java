package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Image;
import io.stitch.stitch.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;


public interface ImageRepository extends MongoRepository<Image, String> {
    Optional<Image> findByUrl(String URL);
}
