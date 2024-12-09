package com.sher_islam.companyms.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/companies")
public class CompanyController {
    private CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @GetMapping
    public ResponseEntity<List<Company>> getAllCompanies(){
        return new ResponseEntity<>(companyService.getAllCompanies(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<String> updateCompany(@RequestBody Company updatedCompany,
                                                @PathVariable Long id){
        Boolean updated =companyService.updateCompany(updatedCompany,id);
        if(updated) {
            return new ResponseEntity<>("Company successfully updated",
                    HttpStatus.OK);
        }
        return new ResponseEntity<>("Company not found",
                HttpStatus.NOT_FOUND);
    }
    @PostMapping
    public ResponseEntity<String> createCompany(@RequestBody Company company){
        companyService.createCompany(company);
        return new ResponseEntity<>("Company added successfully",
                HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteCompany(@PathVariable Long id){
        Boolean isDeleted=companyService.deleteCompanyById(id);
        if(isDeleted){
            return new ResponseEntity<>("Company Successfully deleted",HttpStatus.OK);

        }
        return new ResponseEntity<>("Company Not found",HttpStatus.NOT_FOUND);

    }
    @GetMapping("/{id}")
    public ResponseEntity<Company> getCompany(@PathVariable Long id){
       Company company = companyService.getCompanyById(id);
       if(company!=null){
           return new ResponseEntity<>(company,HttpStatus.OK);
       }else{
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }

    }
}
