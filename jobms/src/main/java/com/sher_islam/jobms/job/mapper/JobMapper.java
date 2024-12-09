package com.sher_islam.jobms.job.mapper;

import com.sher_islam.jobms.job.dto.JobDTO;
import com.sher_islam.jobms.job.Job;
import com.sher_islam.jobms.job.external.Company;
import com.sher_islam.jobms.job.external.Review;

import java.util.List;

public class JobMapper {
    public static JobDTO mapToJobDTO(
            Job job, Company company, List<Review> reviews){
        JobDTO jobDTO =new JobDTO();
        jobDTO.setId(job.getId());
        jobDTO.setTitle(job.getTitle());
        jobDTO.setDescription(job.getDescription());
        jobDTO.setLocation(job.getLocation());
        jobDTO.setMaxSalary(job.getMaxSalary());
        jobDTO.setMinSalary(job.getMinSalary());
        jobDTO.setCompany(company);
        jobDTO.setReviews(reviews);

        return jobDTO;
    }
}
