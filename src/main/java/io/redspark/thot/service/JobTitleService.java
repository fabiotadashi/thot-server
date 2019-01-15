package io.redspark.thot.service;

import io.redspark.thot.controller.dto.JobTitleDTO;
import io.redspark.thot.controller.dto.UserDTO;

import java.util.List;

public interface JobTitleService {
    JobTitleDTO create(JobTitleDTO jobTitleDTO);

    List<JobTitleDTO> findAll();
}
