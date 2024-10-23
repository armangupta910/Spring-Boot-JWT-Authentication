package com.demo.myFirstProject.service;

import com.demo.myFirstProject.model.Demo;
import com.demo.myFirstProject.repository.DemoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {
    private final DemoRepository demoRepository;

    @Autowired
    public DemoService(DemoRepository demoRepository) {
        this.demoRepository = demoRepository;
    }

    public List<Demo> getAllDemos() {
        return demoRepository.findAll();
    }
}

