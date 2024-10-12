package io.stitch.stitch.repos;

import io.stitch.stitch.domain.Brand;
import io.stitch.stitch.domain.Category;
import io.stitch.stitch.domain.Machine;
import io.stitch.stitch.domain.Tag;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByBrand(Brand brand);

    Machine findFirstByTags(Tag tag);

    Machine findFirstByCategory(Category category);

    List<Machine> findAllByTags(Tag tag);

}
