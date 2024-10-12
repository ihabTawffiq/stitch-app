package io.stitch.stitch.repos;

import io.stitch.stitch.domain.Category;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface CategoryRepository extends MongoRepository<Category, Long> {
}
