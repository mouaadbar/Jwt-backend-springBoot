package com.example.JwtPractice.controller;

import com.example.JwtPractice.dto.JwtAuthResponse;
import com.example.JwtPractice.dto.LoginDto;
import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.security.jwt.JwtProvider;
import com.example.JwtPractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @Autowired
    JwtProvider jwtProvider;

    @PostMapping("sign-in")
    public ResponseEntity<JwtAuthResponse>login(@RequestBody LoginDto loginDto){

          Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                  loginDto.getUsername(), loginDto.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(auth);
        String token = jwtProvider.generateToken(auth);

        return ResponseEntity.ok(new JwtAuthResponse(token));
    }


    @PostMapping("sign-up")
    public ResponseEntity<String> saveUser(@RequestBody UserDto userDto){
        userService.saveUser(userDto);
        return new ResponseEntity<>("user successfully created", HttpStatus.CREATED);
    }
}
