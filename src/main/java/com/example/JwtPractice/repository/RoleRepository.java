package com.example.JwtPractice.repository;

import com.example.JwtPractice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByRoleName(@Param("roleName") String roleName);
}
