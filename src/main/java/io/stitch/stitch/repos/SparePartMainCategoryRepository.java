package io.stitch.stitch.repos;

import io.stitch.stitch.entity.SparePartMainCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface SparePartMainCategoryRepository extends MongoRepository<SparePartMainCategory, Long> {
    List<SparePartMainCategory> findAllByIsHomepageCategory(Boolean isHomepageBrand, Sort sort);
}
