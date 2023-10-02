package com.example.JwtPractice.repository;

import com.example.JwtPractice.entities.Role;
import com.example.JwtPractice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;

public interface UserRepository extends JpaRepository<User, Long> {


    Optional<User> findByUsername(@Param("username")String username);

    @Modifying
    @Query("UPDATE User u SET u.roles = :roles WHERE u.id = :userId")
    void updateRoles(@Param("userId") Long userId, @Param("roles") Set<Role> roles);

}
