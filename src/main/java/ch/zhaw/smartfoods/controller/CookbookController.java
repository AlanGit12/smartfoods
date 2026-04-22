package ch.zhaw.smartfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.Cookbook.Cookbook;
import ch.zhaw.smartfoods.model.Recipe.UserRecipe;
import ch.zhaw.smartfoods.service.CookbookService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class CookbookController {

    @Autowired
    CookbookService cookbookService;

    @GetMapping("/cookbook")
    public ResponseEntity<Cookbook> getCookbook() {
        try {
            Cookbook book = cookbookService.getOrCreateCookbook();
            return ResponseEntity.ok(book);

        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cookbook/save/{recipeId}")
    public ResponseEntity<Cookbook> saveRecipe(@PathVariable String recipeId) {
        try {
            return ResponseEntity.ok(cookbookService.saveRecipe(recipeId));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/cookbook/save/{recipeId}")
    public ResponseEntity<Void> removeRecipe(@PathVariable String recipeId) {
        try {
            cookbookService.removeRecipe(recipeId);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/cookbook/recipes")
    public ResponseEntity<Cookbook> addUserRecipe(@RequestBody UserRecipe recipe) {
        try {
            return ResponseEntity.ok(cookbookService.addUserRecipe(recipe));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/cookbook/recipes/{index}")
    public ResponseEntity<Void> removeUserRecipe(@PathVariable int index) {
        try {
            cookbookService.removeUserRecipe(index);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
