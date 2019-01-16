package io.redspark.thot.service.impl;

import io.redspark.thot.controller.dto.UserDTO;
import io.redspark.thot.model.User;
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
    private final ModelMapper modeMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, ModelMapper modeMapper) {
        this.userRepository = userRepository;
        this.modeMapper = modeMapper;
    }

    @Override
    public UserDTO create(UserDTO userDTO) {

        User user = modeMapper.map(userDTO, User.class);

        userRepository.save(user);

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
