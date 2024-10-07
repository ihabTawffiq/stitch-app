package io.stitch.stitch.repos;

import io.stitch.stitch.domain.Machine;
import io.stitch.stitch.domain.Tag;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface MachineRepository extends MongoRepository<Machine, Long> {

    Machine findFirstByTags(Tag tag);

    List<Machine> findAllByTags(Tag tag);

}
