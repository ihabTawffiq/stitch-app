package io.stitch.stitch.repos;

import io.stitch.stitch.domain.Brand;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface BrandRepository extends MongoRepository<Brand, Long> {
}
