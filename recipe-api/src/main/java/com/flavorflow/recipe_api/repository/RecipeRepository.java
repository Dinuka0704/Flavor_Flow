package com.flavorflow.recipe_api.repository;

import com.flavorflow.recipe_api.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    // All CRUD methods are now ready!
}