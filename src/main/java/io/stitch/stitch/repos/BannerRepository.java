package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Banner;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BannerRepository extends MongoRepository<Banner, Long> {
}
