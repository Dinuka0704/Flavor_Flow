package com.flavorflow.recipe_api.repository;

import com.flavorflow.recipe_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom method: Spring Data JPA automatically provides implementation
    Optional<User> findByUsername(String username);
}