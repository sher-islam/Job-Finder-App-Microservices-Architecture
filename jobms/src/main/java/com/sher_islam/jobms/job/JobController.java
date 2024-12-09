package com.sher_islam.jobms.job;

import com.sher_islam.jobms.job.dto.JobDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> findAll() {

        return ResponseEntity.ok(jobService.findAll());
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job added successfully",HttpStatus.OK);

    }
    @GetMapping("/{id}")
    public ResponseEntity<JobDTO> getJobById(@PathVariable Long id){
        JobDTO jobDTO =jobService.getJobById(id);
        if(jobDTO !=null){
            return new ResponseEntity<>(jobDTO,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJobById(@PathVariable Long id){
        boolean deleted=jobService.deleteJobById(id);
        if(deleted){
            return ResponseEntity.ok("Job deleted successfully");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //@RequestMapping(value = "/jobs/{id}",method = RequestMethod.PUT)
    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id,
                             @RequestBody Job updatedJob){
        Boolean updated =jobService.updateJob(id,updatedJob);
        if(updated)
        {
            return ResponseEntity.ok("Job updated successfully");
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
