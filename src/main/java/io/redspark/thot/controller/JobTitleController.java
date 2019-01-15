package io.redspark.thot.controller;

import io.redspark.thot.controller.dto.JobTitleDTO;
import io.redspark.thot.controller.dto.UserDTO;
import io.redspark.thot.service.JobTitleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("job-titles")
public class JobTitleController {

    private JobTitleService jobTitleService;

    @Autowired
    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @PostMapping
    public JobTitleDTO create(@RequestBody JobTitleDTO jobTitleDTO) {
        return jobTitleService.create(jobTitleDTO);
    }

    @GetMapping
    public List<JobTitleDTO> findAll() {
        return jobTitleService.findAll();
    }

}
