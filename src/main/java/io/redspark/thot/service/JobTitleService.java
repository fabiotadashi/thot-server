package io.redspark.thot.service;

import io.redspark.thot.controller.dto.JobTitleDTO;

import java.util.List;

public interface JobTitleService {
    List<JobTitleDTO> findAll();
}
