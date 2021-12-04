package org.vietsearch.essme.repository.academic_rank;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.vietsearch.essme.model.academic_rank.AcademicRank;

import java.util.List;

public interface AcademicRankRepository extends MongoRepository<AcademicRank, String> {
    List<AcademicRank> findBy(TextCriteria textCriteria);
}
