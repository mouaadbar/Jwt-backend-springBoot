package com.example.JwtPractice.controller;

import com.example.JwtPractice.configuration.CustomUserDetailsService;
import com.example.JwtPractice.dto.JwResponse;
import com.example.JwtPractice.dto.LoginDto;
import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private UserService userService;


    @PostMapping("sign-in")
    public ResponseEntity<JwResponse>createToken(@RequestBody LoginDto loginDto) throws Exception {

        return new ResponseEntity<JwResponse>(userDetailsService.createJwtToken(loginDto), HttpStatus.CREATED);
    }

    @PostMapping("sign-up")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        User user = userService.findUserByUsername(userDto.getUsername());
        if(user != null){
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return new ResponseEntity<UserDto>(userService.createUser(userDto),HttpStatus.CREATED);
    }
}
