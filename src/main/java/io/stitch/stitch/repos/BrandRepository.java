package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface BrandRepository extends MongoRepository<Brand, Long> {
    List<Brand> findAllByIsHomepageBrand(Boolean isHomepageBrand, Sort sort);
}
