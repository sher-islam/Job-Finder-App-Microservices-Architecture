package com.sher_islam.jobms.job;

import com.sher_islam.jobms.job.dto.JobDTO;

import java.util.List;

public interface JobService {
    List<JobDTO> findAll();
    void createJob(Job job);

    JobDTO getJobById(Long id);

    boolean deleteJobById(Long id);

    Boolean updateJob(Long id, Job updatedJob);
}
