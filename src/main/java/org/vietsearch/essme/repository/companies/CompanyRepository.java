package org.vietsearch.essme.repository.companies;

import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.vietsearch.essme.model.companies.Company;

import java.util.List;

public interface CompanyRepository extends MongoRepository<Company, String> {
    List<Company> findBy(TextCriteria textCriteria);
}
