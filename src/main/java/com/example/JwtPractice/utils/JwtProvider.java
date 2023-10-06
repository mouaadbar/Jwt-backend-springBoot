package com.example.JwtPractice.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtProvider {
    @Value("${app.jwt.secretKey}")
    private String secretKey;
    @Value("${app.jwt.experitionInMs}")
    private Long expiredInMs;



    public String generateToken(UserDetails userDetails){
        Map<String, Object> claims = new HashMap<>();
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+ expiredInMs))
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
    }



    public String getUsernameFromToken(String token){

        return getClaimsFromToken(token, Claims::getSubject);

    }

    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimResolver){
        final Claims claims =getAllClaimsFromToken(token);
        return claimResolver.apply(claims);


    }

    private Claims getAllClaimsFromToken(String token){
        return Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();
    }







    public boolean validateToken(String token, UserDetails userDetails) {
           String username = getUsernameFromToken(token);
           return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    private boolean isTokenExpired(String token){
        final Date expDate = getExpirationDateFromToKen(token);
        return expDate.before(new Date());

    }

    private Date getExpirationDateFromToKen(String token){
            return getClaimsFromToken(token, Claims::getExpiration);

    }
}
