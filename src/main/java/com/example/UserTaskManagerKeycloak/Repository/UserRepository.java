package com.example.UserTaskManagerKeycloak.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.UserTaskManagerKeycloak.Entity.AppUser;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByUsername(String username);
    Optional<AppUser> findByKeycloakId(String keycloakId);
}
