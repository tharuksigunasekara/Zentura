package com.example.zenturaserver.repository;

import com.example.zenturaserver.model.Job;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobRepository extends MongoRepository<Job,String> {



}
