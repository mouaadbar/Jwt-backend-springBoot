package com.example.JwtPractice.service;

import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.repository.RoleRepository;
import com.example.JwtPractice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


@Override
    public UserDto saveUser(UserDto userDto){

        User user = mapDtoToEntity(userDto);



        Role roles = roleRepository.findById(1L).get();
        user.setRoles(Collections.singleton(roles));

        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRoles(Collections.singleton(roles));

        User saveUser = userRepository.save(user);
        UserDto userDtoFinal = mapEntityToDto(saveUser);

        System.out.println(userDtoFinal);

        return userDtoFinal;

    }

    @Override
    public Optional<User> findByUsername(String username){
       Optional<User> user = userRepository.findByUsername(username);
       return user;
    }

    @Override
    public void makeUserAdmin(Long userId) {

    User user = userRepository.findById(userId).get();
    Role roleAdmin = roleRepository.findByRoleName("ROLE_ADMIN").get();

    if(user!= null && roleAdmin != null){

        Set<Role> roleAd = new HashSet<>();
        roleAd.add(roleAdmin);
        user.setRoles(roleAd);
        userRepository.save(user);

    }
    }


    @Override
    public List<UserDto> findAllUsers(){

        List<User> users =  userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return userDtos;

    }








    public User mapDtoToEntity(UserDto userDto){
     User user = modelMapper.map(userDto, User.class);
     return user;
 }

 public UserDto mapEntityToDto(User user){
     UserDto userDto = modelMapper.map(user, UserDto.class);
     return userDto;
 }
}
