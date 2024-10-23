package com.demo.myFirstProject.repository;

import com.demo.myFirstProject.model.Demo;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DemoRepository extends MongoRepository<Demo, String> {
}

