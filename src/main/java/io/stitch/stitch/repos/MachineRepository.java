package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Brand;
import io.stitch.stitch.entity.Category;
import io.stitch.stitch.entity.Machine;
import io.stitch.stitch.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Collection;
import java.util.List;

public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByBrand(final Brand brand);

    Page<Machine> findAllByTagsIn(final Collection<Tag> tags, final Pageable pageable);
    List<Machine> findAllByTagsIn(final Collection<Tag> tags);

    Machine findFirstByCategory(final Category category);

    List<Machine> findAllByTags(final Tag tag);


    Page<Machine> findAllByCategory(final Category categoryId,final Pageable pageable);
    List<Machine> findAllByCategory(final Category categoryId);

    Page<Machine> findAllByBrand(final Brand brandId,final Pageable pageable);
    List<Machine> findAllByBrand(final Brand brandId);

}
