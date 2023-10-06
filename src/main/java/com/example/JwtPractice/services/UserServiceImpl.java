package com.example.JwtPractice.services;

import com.example.JwtPractice.dto.RoleDto;
import com.example.JwtPractice.dto.UserDto;
import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.repository.RoleRepository;
import com.example.JwtPractice.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    PasswordEncoder passwordEncoder;



    @Override
    public UserDto createUser(UserDto userDto){

     User user = mapDtoToEntity(userDto);


        Set<Role> role = new HashSet<>();
         Role findRole = roleRepository.findById(2L).get();
         role.add(findRole);
         user.setRoles(role);
         user.setPassword(passwordEncoder.encode(userDto.getPassword()));
         System.out.println("---------------------------"+user+"----------------------------------");
         userRepository.save(user);
         UserDto finalUserDto = mapEntityToDto(user);
         System.out.println(finalUserDto);
         return finalUserDto;

    }
    @Override
    public User findUserByUsername (String username){
          User user = userRepository.findByUsername(username);
          return user;

    }
    @Override
    public void makeAdmin(Long userId){


        Role adminRole =roleRepository.findById(1L).get();
        User user = userRepository.findById(userId).get();

        if(user!=null && adminRole!=null){
             Set<Role> role = new HashSet<>();
             role.add(adminRole);
             user.setRoles(role);
             userRepository.save(user);
         }







    }

    @Override
    public List<UserDto> findAllUsers(){
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(this::mapEntityToDto).collect(Collectors.toList());
        return userDtos;
    }









    public UserDto mapEntityToDto(User user){
        UserDto userDto = modelMapper.map(user, UserDto.class);
        return userDto;
    }



    public User mapDtoToEntity(UserDto userDto){
        User user = modelMapper.map(userDto, User.class);
        return user;
    }

}
