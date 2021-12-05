package org.vietsearch.essme.repository.companies;

import org.vietsearch.essme.model.companies.Company;

import java.util.List;

public interface CompanyCustomRepo {
    List<Company> getCompaniesByCountryIndustryRank(String country, String rank, String industry);

    ;
}
