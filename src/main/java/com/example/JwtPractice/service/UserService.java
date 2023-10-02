package com.example.JwtPractice.service;

import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.entities.User;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UserService {
    UserDto saveUser(UserDto userDto);

    Optional<User> findByUsername(String username);

    void makeUserAdmin (Long userId);

    List<UserDto> findAllUsers();
}
