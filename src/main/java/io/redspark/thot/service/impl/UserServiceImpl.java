package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.CreateUserDTO;
import io.redspark.thot.controller.dto.UserDTO;
import io.redspark.thot.model.JobTitle;
import io.redspark.thot.model.User;
import io.redspark.thot.repository.JobTitleRepository;
import io.redspark.thot.repository.UserRepository;
import io.redspark.thot.security.AuthenticationUtils;
import io.redspark.thot.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private JobTitleRepository jobTitleRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           JobTitleRepository jobTitleRepository,
                           ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jobTitleRepository = jobTitleRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDTO create(CreateUserDTO createUserDTO) {

        User user = modelMapper.map(createUserDTO, User.class);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<JobTitle> jobTitleList = jobTitleRepository.findAllById(createUserDTO.getJobTitleIdList());

        user.setJobTitleList(jobTitleList);

        user = userRepository.save(user);

        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO getCurrentUser() {
        String userName = AuthenticationUtils.getAuthenticatedUserName();
        User user = userRepository.findByName(userName);
        return modelMapper.map(user, UserDTO.class);
    }
}
