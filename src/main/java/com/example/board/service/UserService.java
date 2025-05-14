package com.example.board.service;

import com.example.board.dto.UserDTO;
import com.example.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(UserDTO userDTO) {
        userRepository.save(userDTO);
    }

    public UserDTO login(UserDTO userDTO) {
        return userRepository.login(userDTO);
    }
}
