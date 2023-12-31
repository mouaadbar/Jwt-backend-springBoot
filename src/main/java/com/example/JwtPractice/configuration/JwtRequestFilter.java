package com.example.JwtPractice.configuration;

import com.example.JwtPractice.utils.JwtProvider;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CustomUserDetailsService customUserDetailsService;


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;


        if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
             jwtToken = token.substring(7);

            try {

                username =jwtProvider.getUsernameFromToken(jwtToken);

            } catch (IllegalArgumentException ex) {
                System.out.println("unable to get jwt token");
            } catch (ExpiredJwtException ex) {
                System.out.println("jwt token is expired");
            }

        }else {
            System.out.println("-------------toke is null---------------");
        }





        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);

        if(jwtProvider.validateToken(jwtToken , userDetails)){
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                    (userDetails.getUsername(), null, userDetails.getAuthorities());

            usernamePasswordAuthenticationToken.
                    setDetails(new WebAuthenticationDetailsSource().buildDetails(request));


        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        }

        }

        filterChain.doFilter(request, response);
    }
}
