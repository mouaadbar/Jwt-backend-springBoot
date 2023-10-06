package com.example.JwtPractice.dto;

import com.example.JwtPractice.entities.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwResponse {
 private User user;
 private String token;

}
