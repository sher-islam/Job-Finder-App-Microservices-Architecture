package com.sher_islam.jobms.job.impl;


import com.sher_islam.jobms.job.Job;
import com.sher_islam.jobms.job.JobRepository;
import com.sher_islam.jobms.job.JobService;
import com.sher_islam.jobms.job.clients.CompanyClient;
import com.sher_islam.jobms.job.clients.ReviewClient;
import com.sher_islam.jobms.job.dto.JobDTO;
import com.sher_islam.jobms.job.external.Company;
import com.sher_islam.jobms.job.external.Review;
import com.sher_islam.jobms.job.mapper.JobMapper;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JobServiceImpl implements JobService {

    //private List<Job> jobs = new ArrayList<>();
    JobRepository jobRepository;

    @Autowired
    private RestTemplate restTemplate;
    private CompanyClient companyClient;
    private ReviewClient reviewClient;

    int attempt=0;//this variable is created just to check no of retries by findAll method since we have annotated it with @Retry

    public JobServiceImpl(JobRepository jobRepository,
                          CompanyClient companyClient,
                          ReviewClient reviewClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
        this.reviewClient = reviewClient;
    }

    @Override
//    @CircuitBreaker(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
//    @Retry(name="companyBreaker",
//            fallbackMethod = "companyBreakerFallback")//this is yet another way
//    @RateLimiter(name = "companyBreaker",
//            fallbackMethod = "companyBreakerFallback")
    public List<JobDTO> findAll() {
        System.out.println("Attempt: "+ ++attempt);
        List<Job> jobs = jobRepository.findAll();
        List<JobDTO> jobDTOS = new ArrayList<>();

        return jobs.stream().map(this::converToDTO).collect(Collectors.toList());
    }
    public List<String> companyBreakerFallback(Exception e){
        List<String> list=new ArrayList<>();
        list.add("Dummy");
        return list;
    }
    private JobDTO converToDTO(Job job) {

        //this implementation is using RestTemplate
//        Company company = restTemplate.getForObject(
//                "http://COMPANY-SERVICE:8081/companies/" + job.getCompanyId(), Company.class);

//        ResponseEntity<List<Review>> reviewResponse = restTemplate.exchange(
//                "http://REVIEW-SERVICE:8083/reviews?companyId=" + job.getCompanyId(),
//                HttpMethod.GET,
//                null,
//                new ParameterizedTypeReference<List<Review>>() {
//                }
//        );
//        List<Review> reviews = reviewResponse.getBody();
        Company company = companyClient.getCompany(job.getId());
        List<Review> reviews = reviewClient.getReviews(job.getCompanyId());
        JobDTO jobDTO = JobMapper
                .mapToJobDTO(job, company, reviews);

        return jobDTO;


    }

    @Override
    public void createJob(Job job) {

        jobRepository.save(job);

    }

    @Override
    public JobDTO getJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);

        return converToDTO(job);


    }

    @Override
    public boolean deleteJobById(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }

    }

    @Override
    public Boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);

        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMaxSalary(updatedJob.getMaxSalary());
            job.setMinSalary(updatedJob.getMinSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

}



