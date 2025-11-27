package com.flavorflow.recipe_api.controller;

import com.flavorflow.recipe_api.model.Recipe;
import com.flavorflow.recipe_api.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as a REST Controller
@RequestMapping("/api/recipes") // Base URL for all endpoints in this controller
// IMPORTANT: Enable CORS to allow React (port 3000) to communicate with Spring (port 8080)
@CrossOrigin(origins = "http://localhost:3000")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    // 1. GET ALL RECIPES: GET http://localhost:8080/api/recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        List<Recipe> recipes = recipeService.findAllRecipes();
        return new ResponseEntity<>(recipes, HttpStatus.OK);
    }

    // 2. GET RECIPE BY ID: GET http://localhost:8080/api/recipes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable Long id) {
        return recipeService.findRecipeById(id)
                .map(recipe -> new ResponseEntity<>(recipe, HttpStatus.OK)) // Found
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // Not Found
    }

    // 3. CREATE NEW RECIPE: POST http://localhost:8080/api/recipes
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.saveRecipe(recipe);
        // Returns the created recipe with a 201 Created status
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

    // 4. UPDATE RECIPE: PUT http://localhost:8080/api/recipes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable Long id, @RequestBody Recipe recipeDetails) {
        return recipeService.findRecipeById(id)
                .map(existingRecipe -> {
                    // Update fields of the existing recipe
                    existingRecipe.setTitle(recipeDetails.getTitle());
                    existingRecipe.setDescription(recipeDetails.getDescription());
                    existingRecipe.setIngredients(recipeDetails.getIngredients());
                    existingRecipe.setPrepTimeMinutes(recipeDetails.getPrepTimeMinutes());
                    existingRecipe.setServings(recipeDetails.getServings());

                    // Save and return the updated recipe
                    Recipe updatedRecipe = recipeService.saveRecipe(existingRecipe);
                    return new ResponseEntity<>(updatedRecipe, HttpStatus.OK);
                })
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // 5. DELETE RECIPE: DELETE http://localhost:8080/api/recipes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteRecipe(@PathVariable Long id) {
        recipeService.deleteRecipe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Success, but nothing to return
    }
}