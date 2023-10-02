package com.example.JwtPractice.security;

import com.example.JwtPractice.security.jwt.JwtAuthEntryPoint;
import com.example.JwtPractice.security.jwt.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthEntryPoint jwtAuthEntryPoint;
    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder());
    }

    @Bean
    public JwtAuthFilter jwtAuthFilter(){
        return new JwtAuthFilter();
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
       //http.cors().disable();
       http.csrf().disable()
               .exceptionHandling()
               .authenticationEntryPoint(jwtAuthEntryPoint)
               .and()
               .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authorizeRequests()
               .antMatchers(HttpMethod.POST,"/api/user/**").permitAll()
               .antMatchers(HttpMethod.POST, "/api/auth/**").permitAll()
               .anyRequest().fullyAuthenticated();
       http.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);
       //http.authorizeRequests().antMatchers("/api/user/**").permitAll();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    //@Override
    //@Bean
    //protected UserDetailsService userDetailsService(){
      //  UserDetails admin  = User.builder().username("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").build();
       // UserDetails user = User.builder().username("user").password(passwordEncoder().encode("user")).roles("USER").build();
        //return new InMemoryUserDetailsManager(admin, user);
    //}

}


