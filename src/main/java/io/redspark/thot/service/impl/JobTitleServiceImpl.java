package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.JobTitleDTO;
import io.redspark.thot.repository.JobTitleRepository;
import io.redspark.thot.service.JobTitleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class JobTitleServiceImpl implements JobTitleService {

    private final JobTitleRepository jobTitleRepository;
    private ModelMapper modelMapper;

    @Autowired
    public JobTitleServiceImpl(JobTitleRepository jobTitleRepository,
                               ModelMapper modelMapper) {
        this.jobTitleRepository = jobTitleRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<JobTitleDTO> findAll() {
        return jobTitleRepository.findAll()
                .stream()
                .map(jobTitle -> modelMapper.map(jobTitle, JobTitleDTO.class))
                .collect(Collectors.toList());
    }
}
