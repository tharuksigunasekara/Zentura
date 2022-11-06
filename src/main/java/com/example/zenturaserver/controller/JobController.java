package com.example.zenturaserver.controller;

import com.example.zenturaserver.model.Job;
import com.example.zenturaserver.model.Job;

import com.example.zenturaserver.repository.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
public class JobController {

    @Autowired
    private JobRepository JobRepo;

    // -----    GET      -----
    @GetMapping("/job/getall")
    public ResponseEntity<?> getAllJobs() {
        List<Job> jobs = JobRepo.findAll();

        if (jobs.size() > 0) {
            return new ResponseEntity<List<Job>>(jobs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No jobs found!", HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/job/get/{id}")
    public ResponseEntity<?> getAJob(@PathVariable("id") String id) {

        Optional<Job> jobOptional = JobRepo.findById(id);

        if (jobOptional.isPresent()) {
            return new ResponseEntity<Job>(jobOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>("job not found!", HttpStatus.NOT_FOUND);
        }

    }

    // -----    POST      -----
    @PostMapping("/job/create")
    public ResponseEntity<?> createJob(@RequestBody Job job) {
        try {

            JobRepo.save(job);
            return new ResponseEntity<Job>(job, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // -----    UPDATE      -----
    @PutMapping("/job/update/{id}")
    public ResponseEntity<?> updateAJob(@PathVariable("id") String id, @RequestBody Job job) {

        Optional<Job> jobOptional = JobRepo.findById(id);

        if (jobOptional.isPresent()) {
            Job jobToSave = jobOptional.get();
            if(job.getDoctorName()!=null && !job.getDoctorName().isEmpty()){
                jobToSave.setDoctorName(job.getDoctorName());
            }
            if(job.getPatientName()!=null && !job.getPatientName().isEmpty()){
                jobToSave.setPatientName(job.getPatientName());
            }
            if(job.getDescription()!=null && !job.getDescription().isEmpty()){
                jobToSave.setDescription(job.getDescription());
            }

            if(job.getDate()!=null ){
                jobToSave.setDate(job.getDate());
            }

            JobRepo.save(jobToSave);



            return new ResponseEntity<Job>(jobToSave, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Job not found!", HttpStatus.NOT_FOUND);
        }

    }

    // -----    DELETE      -----
    @DeleteMapping("/job/delete/{id}")
    public ResponseEntity<?> deleteAPatient(@PathVariable("id") String id) {
        try {
            JobRepo.deleteById(id);
            return new ResponseEntity<>("Job successfully deleted!", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
