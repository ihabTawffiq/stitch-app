package io.stitch.stitch.service;

import io.stitch.stitch.entity.PrimarySequence;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
public class PrimarySequenceService {

    private static final String PRIMARY_SEQUENCE = "primarySequence";

    private final MongoOperations mongoOperations;

    public PrimarySequenceService(final MongoOperations mongoOperations) {
        this.mongoOperations = mongoOperations;
    }

    public long getNextValue() {
        PrimarySequence primarySequence = mongoOperations.findAndModify(
                query(where("_id").is(PRIMARY_SEQUENCE)),
                new Update().inc("seq", 1),
                options().returnNew(true),
                PrimarySequence.class);
        if (primarySequence == null) {
            primarySequence = new PrimarySequence();
            primarySequence.setId(PRIMARY_SEQUENCE);
            primarySequence.setSeq(10000);
            mongoOperations.insert(primarySequence);
        }
        return primarySequence.getSeq();
    }

}
