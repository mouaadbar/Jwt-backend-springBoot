package com.example.JwtPractice.services;

import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);

    User findUserByUsername(String username);

    void makeAdmin(Long userId);

    List<UserDto> findAllUsers();
}
