package org.vietsearch.essme.repository;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.vietsearch.essme.model.University;

import java.util.List;

public interface UniversityRepository extends MongoRepository<University, String> {
    List<University> findBy(TextCriteria textCriteria);
}
