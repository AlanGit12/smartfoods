package ch.zhaw.smartfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.Recipe.Recipe;
import ch.zhaw.smartfoods.model.Recipe.RecipeCreateDTO;
import ch.zhaw.smartfoods.service.RecipeService;
import ch.zhaw.smartfoods.service.UserService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class RecipeController {

    @Autowired
    RecipeService recipeService;

    @Autowired
    UserService userService;

    @PostMapping("/recipes")
    public ResponseEntity<Recipe> createRecipe(@RequestBody RecipeCreateDTO dto) {
        if (!userService.userHasRole("admin")) { // ← neu
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        try {
            Recipe saved = recipeService.createRecipe(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/recipes")
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        try {
            return ResponseEntity.ok(recipeService.getAllRecipes());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/recipes/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        try {
            return ResponseEntity.ok(recipeService.getRecipeById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/recipes/{id}")
    public ResponseEntity<Void> deleteRecipeById(@PathVariable String id) {
        try {
            recipeService.deleteRecipe(id);
            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
