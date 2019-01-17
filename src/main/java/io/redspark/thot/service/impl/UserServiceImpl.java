package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.CreateUserDTO;
import io.redspark.thot.controller.dto.UserDTO;
import io.redspark.thot.model.JobTitle;
import io.redspark.thot.model.User;
import io.redspark.thot.repository.JobTitleRepository;
import io.redspark.thot.repository.UserRepository;
import io.redspark.thot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private JobTitleRepository jobTitleRepository;
    private final ModelMapper modeMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JobTitleRepository jobTitleRepository,
                           ModelMapper modeMapper) {
        this.userRepository = userRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.modeMapper = modeMapper;
    }

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {

        User user = modeMapper.map(createUserDTO, User.class);

        List<JobTitle> jobTitleList = jobTitleRepository.findAllById(createUserDTO.getJobTitleIdList());

        user.setJobTitleList(jobTitleList);

        user = userRepository.save(user);

        return modeMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modeMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }
}
