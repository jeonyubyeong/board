package com.example.board.repository;

import com.example.board.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserRepository {
    void save(UserDTO userDTO);
    UserDTO login(UserDTO userDTO);
}
