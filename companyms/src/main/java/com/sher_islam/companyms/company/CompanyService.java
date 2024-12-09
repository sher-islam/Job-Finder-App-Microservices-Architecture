package com.sher_islam.companyms.company;

import com.sher_islam.companyms.company.dto.ReviewMessage;

import java.util.List;

public interface CompanyService {
    List<Company> getAllCompanies();
    Boolean updateCompany(Company updatedCompany,Long id);
    void createCompany(Company company);
    Boolean deleteCompanyById(Long id);
    Company getCompanyById(Long id);
    void updateCompanyRating(ReviewMessage reviewMessage);

}
