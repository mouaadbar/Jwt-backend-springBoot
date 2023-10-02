package com.example.JwtPractice.security;

import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
@Service
public class CustomUserDetailsService  implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
          User user = userRepository.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("user not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),authorities(user.getRoles()));

    }


    public Collection<SimpleGrantedAuthority> authorities(Set<Role> roles){
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName())).collect(Collectors.toList());
    }
}
