package org.vietsearch.essme.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.vietsearch.essme.model.University;

public interface UniversityRepository extends MongoRepository<University, String> {
}
