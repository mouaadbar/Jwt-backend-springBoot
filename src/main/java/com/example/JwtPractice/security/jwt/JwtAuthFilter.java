package com.example.JwtPractice.security.jwt;

import com.example.JwtPractice.security.CustomUserDetailsService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthFilter  extends OncePerRequestFilter {

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @SneakyThrows
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String token = getJwtFromToken(request);
        if(StringUtils.hasText(token) && jwtProvider.validateToken(token)){
            String username = jwtProvider.getUsernameFromToken(token);
            // load the user associate with the token

            UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken
                            (userDetails,null,
                                    userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request ));
            // set spring security
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }

        filterChain.doFilter(request, response);

        //getT the token from the request

    }

    private String getJwtFromToken(HttpServletRequest req){
              String bearerToken =req.getHeader("Authorization");
              if(StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
                 return bearerToken.substring(7, bearerToken.length());

              }

              return  null;

    }


}
