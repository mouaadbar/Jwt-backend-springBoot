package com.example.JwtPractice.dto;

import com.example.JwtPractice.entities.Role;
import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private Role role;
}
