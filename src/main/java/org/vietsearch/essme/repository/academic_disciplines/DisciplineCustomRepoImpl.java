package org.vietsearch.essme.repository.academic_disciplines;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;

import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.vietsearch.essme.model.academic_disciplines.Discipline;

import java.util.List;


@Repository
public class DisciplineCustomRepoImpl implements DisciplineCustomRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Discipline> findByParentId(String name) {
        return mongoTemplate.find(Query.query(Criteria.where("parent_id").is(name)), Discipline.class);
    }

    @Override
    public List<Object> getNumberOfDisciplinesInEachParent() {
        return mongoTemplate.aggregate(Aggregation.newAggregation(
                        Aggregation.group("parent_id").count().as("count")
                ),
                Discipline.class,
                Object.class).getMappedResults();
    }
}
