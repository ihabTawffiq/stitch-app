package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.SparePartCategory;
import io.stitch.stitch.entity.SparePartMainCategory;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface SparePartCategoryRepository extends MongoRepository<SparePartCategory, Long> {
    List<SparePartCategory> findAllByIsHomepageCategory(Boolean isHomepageBrand, Sort sort);
    List<SparePartCategory> findAllBySparePartMainCategory(SparePartMainCategory sparePartMainCategory);
}
