package ch.zhaw.smartfoods.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.Cookbook.Cookbook;
import ch.zhaw.smartfoods.model.Recipe.UserRecipe;
import ch.zhaw.smartfoods.repository.CookbookRepository;

@Service
public class CookbookService {

    @Autowired
    CookbookRepository cookbookRepository;

    @Autowired
    UserService userService;

    public Cookbook getOrCreateCookbook() {
        String userId = userService.getUserId();

        Optional<Cookbook> existing = cookbookRepository.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get(); //
        }

        Cookbook book = new Cookbook();
        book.setUserId(userId);
        book.setSavedRecipeIds(new ArrayList<>());
        book.setUserRecipes(new ArrayList<>());
        return cookbookRepository.save(book);
    }

    public Cookbook saveRecipe(String recipeId) {
        Cookbook book = getOrCreateCookbook();

        if (!book.getSavedRecipeIds().contains(recipeId)) {
            book.getSavedRecipeIds().add(recipeId);
        }
        return cookbookRepository.save(book);
    }

    public Cookbook removeRecipe(String recipeId) {
        Cookbook book = getOrCreateCookbook();
        book.getSavedRecipeIds().remove(recipeId);
        return cookbookRepository.save(book);
    }

    public Cookbook addUserRecipe(UserRecipe recipe) {
        Cookbook book = getOrCreateCookbook();
        book.getUserRecipes().add(recipe);
        return cookbookRepository.save(book);
    }

    public Cookbook removeUserRecipe(int index) {
        Cookbook book = getOrCreateCookbook();
        book.getUserRecipes().remove(index);
        return cookbookRepository.save(book);
    }
}
