package org.vietsearch.essme.repository.academic_disciplines;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.vietsearch.essme.model.academic_disciplines.Discipline;

import java.util.List;

public interface DisciplineRepository extends MongoRepository<Discipline, String> {
    List<Discipline> findBy(TextCriteria criteria);
}
