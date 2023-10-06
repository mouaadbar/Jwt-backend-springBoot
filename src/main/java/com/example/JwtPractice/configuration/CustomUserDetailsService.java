package com.example.JwtPractice.configuration;

import com.example.JwtPractice.dto.JwResponse;
import com.example.JwtPractice.dto.LoginDto;
import com.example.JwtPractice.entities.User;
import com.example.JwtPractice.repository.UserRepository;
import com.example.JwtPractice.utils.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;

    public JwResponse createJwtToken(LoginDto loginDto) throws Exception {
        String username = loginDto.getUsername();
        String password = loginDto.getPassword();

        authenticate(username, password);


        final UserDetails userDetails = loadUserByUsername(username);

        String newGeneratedToken = jwtProvider.generateToken(userDetails);

       User user = userRepository.findByUsername(username);
       return new JwResponse(user, newGeneratedToken);


    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =userRepository.findByUsername(username);
        if(user!= null){
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    getAuthorities(user)
            );
        }else {
          throw new UsernameNotFoundException("Username is not valid");
        }
    }

private Set getAuthorities(User user) {
        Set authorities = new HashSet();
        user.getRoles().forEach(role->{
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return authorities;
    }



    private void authenticate(String username, String password) throws Exception {

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));

        }catch(DisabledException e){
            throw new Exception("user is disabled");
        }catch (BadCredentialsException e){
            throw new Exception("Bad credantials from the user");

        }

    }


}
