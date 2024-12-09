package com.sher_islam.companyms.company.impl;


import com.sher_islam.companyms.company.CompanyRepository;
import com.sher_islam.companyms.company.Company;
import com.sher_islam.companyms.company.CompanyService;
import com.sher_islam.companyms.company.clients.ReviewClient;
import com.sher_islam.companyms.company.dto.ReviewMessage;
import jakarta.ws.rs.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompanyServiceImpl implements CompanyService {
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository,
                              ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient=reviewClient;
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }

    @Override
    public Boolean updateCompany(Company updatedCompany,Long id) {
        Optional<Company> optionalCompany=companyRepository.findById(id);
        if(optionalCompany.isPresent()){
            Company company=optionalCompany.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());

            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public Boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)){
            companyRepository.deleteById(id);
            return true;
        }
        return false;


    }

    @Override
    public Company getCompanyById(Long id) {

       return companyRepository.findById(id).orElse(null);
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company=companyRepository.findById(reviewMessage.getCompanyId()).orElseThrow(()->new NotFoundException("Company not found"));
        double averageRating=reviewClient.getAverageRatingForCompany(reviewMessage.getCompanyId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }
}
