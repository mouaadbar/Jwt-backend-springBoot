package com.example.JwtPractice.service;

import com.example.JwtPractice.dto.RoleDto;
import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService{
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;


    @Override
    public RoleDto createRole(RoleDto roleDto){

        Role role = dtoToEntity(roleDto);
        roleRepository.save(role);
        RoleDto roleDtofinal = entityToDto(role);
        System.out.println(roleDto);
        return roleDtofinal;

    }



    public RoleDto entityToDto(Role role){
        RoleDto roleDto = modelMapper.map(role, RoleDto.class);
        return roleDto;
    }
    public Role dtoToEntity(RoleDto roleDto){
        Role role = modelMapper.map(roleDto, Role.class);
        return role;
    }








}
