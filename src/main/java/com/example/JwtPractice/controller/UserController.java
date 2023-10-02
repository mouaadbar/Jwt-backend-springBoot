package com.example.JwtPractice.controller;

import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<UserDto>> findAllUsers(){
        return new ResponseEntity<List<UserDto>>(userService.findAllUsers(),HttpStatus.OK);
    }


    @Transactional
    @PostMapping("make-admin/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> makeUserAdmin(@PathVariable("userId")Long userId){
        userService.makeUserAdmin(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
