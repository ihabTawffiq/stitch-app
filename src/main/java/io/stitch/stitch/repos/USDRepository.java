package io.stitch.stitch.repos;

import io.stitch.stitch.entity.Usd;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface USDRepository extends MongoRepository<Usd, Long> {
}
