package com.example.JwtPractice.controller;

import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.repository.UserRepository;
import com.example.JwtPractice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;



    @PostMapping("make-admin/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeAdmin(@PathVariable("userId")Long userId){
        userService.makeAdmin(userId);
        return ResponseEntity.ok("user is now admin");
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> allUsers(){
        return new ResponseEntity<List<UserDto>>(userService.findAllUsers(), HttpStatus.OK);
    }


    @GetMapping("/forAdmin")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> forAdmin(){
        return new ResponseEntity<String>("this only accessible for admin", HttpStatus.OK);
    }

    @GetMapping("/forUser")
    @PreAuthorize("hasAnyRole('ADMIN' ,'USER')")
    public ResponseEntity<String> forUser(){
        return new ResponseEntity<String>("this only accessible for user", HttpStatus.OK);
    }

}

