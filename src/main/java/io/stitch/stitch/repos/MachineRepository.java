package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Category;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByBrand(Brand brand);


    Machine findFirstByCategory(Category category);

    List<Machine> findAllByTags(Tag tag);

    List<Machine> findAllByBrandInAndCategoryInAndTagsIn(List<Brand> brands, List<Category> categories, List<Tag> tags);

    List<Machine> findAllByCategory(Category categoryId);

    List<Machine> findAllByBrand(Brand brandId);

}
