package org.vietsearch.essme.repository.companies;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Repository;
import org.vietsearch.essme.model.companies.Company;

import java.util.List;

@Repository
public class CompanyCustomRepoImpl implements CompanyCustomRepo {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Company> getCompaniesByCountryIndustryRank(String country, String rank, String industry) {
        return mongoTemplate.aggregate(
                Aggregation.newAggregation(
                        Aggregation.match(Criteria.where("country").is(country).and("industries").is(industry)),
                        Aggregation.sort(Sort.by("ranks." + rank).ascending())
                ),
                Company.class,
                Company.class
        ).getMappedResults();
    }
}
