package com.flavorflow.recipe_api.service;

import com.flavorflow.recipe_api.model.Recipe;
import com.flavorflow.recipe_api.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service // Marks this class as a Spring Service component
public class RecipeService {

    private final RecipeRepository recipeRepository;

    // Dependency Injection: Spring automatically provides the RecipeRepository instance
    @Autowired
    public RecipeService(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    // --- READ OPERATIONS ---

    public List<Recipe> findAllRecipes() {
        return recipeRepository.findAll();
    }

    public Optional<Recipe> findRecipeById(Long id) {
        // Optional is used to handle cases where the ID might not exist
        return recipeRepository.findById(id);
    }

    // --- CREATE & UPDATE OPERATIONS ---

    public Recipe saveRecipe(Recipe recipe) {
        // This method handles both new recipe creation and updating an existing one
        return recipeRepository.save(recipe);
    }

    // --- DELETE OPERATION ---

    public void deleteRecipe(Long id) {
        recipeRepository.deleteById(id);
    }
}