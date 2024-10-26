package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Category;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Tag;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByBrand(Brand brand);

    Machine findFirstByTags(Tag tag);

    Machine findFirstByCategory(Category category);

    List<Machine> findAllByTags(Tag tag);

    List<Machine> findAllByBrandIdInAndCategoryIdInAndTagsIdIn(List<Long> brandIds, List<Long> categoryIds, List<Long> tagIds);

    List<Machine> findAllByCategory(Category categoryId);

    List<Machine> findAllByBrand(Brand brandId);

}
