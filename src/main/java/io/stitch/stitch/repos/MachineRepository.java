package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Category;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Tag;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByBrand(Brand brand);

    Machine findFirstByTags(Tag tag);

    Machine findFirstByCategory(Category category);

    List<Machine> findAllByTags(Tag tag);

    List<Machine> findAllByCategory(Category categoryId);
}
