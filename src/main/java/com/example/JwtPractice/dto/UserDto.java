package com.example.JwtPractice.dto;

import com.example.JwtPractice.entities.Role;
import lombok.Data;

import java.util.Set;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String lastName;
    private String username;
    private String password;
    private Set<Role> roles;
}
