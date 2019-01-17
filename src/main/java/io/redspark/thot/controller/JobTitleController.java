package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.JobTitleDTO;
import io.redspark.thot.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("job-titles")
public class JobTitleController {

    private final JobTitleService jobTitleService;

    @Autowired
    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @GetMapping
    private List<JobTitleDTO> findAll(){
        return jobTitleService.findAll();
    }

}
