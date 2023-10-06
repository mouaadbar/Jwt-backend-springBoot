package com.example.JwtPractice.repository;

import com.example.JwtPractice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
}
