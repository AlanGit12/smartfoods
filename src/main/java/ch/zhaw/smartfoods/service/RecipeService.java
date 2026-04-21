package ch.zhaw.smartfoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.Recipe.Recipe;
import ch.zhaw.smartfoods.model.Recipe.RecipeCreateDTO;
import ch.zhaw.smartfoods.repository.RecipeRepository;

@Service
public class RecipeService {
    

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    UserService userService;

    public Recipe createRecipe(RecipeCreateDTO dto){
        String userId = userService.getUserId();

        Recipe create = new Recipe(
            dto.getName(),
            dto.getDescription(),
            dto.getIngredients()
        );
        create.setCreatedBy(userId);
        return recipeRepository.save(create);
    }

    public List<Recipe> getAllRecipes(){
        return recipeRepository.findAll();

    }

    public Recipe getRecipeById(String id){
        return recipeRepository.findById(id)
        .orElseThrow(()-> new RuntimeException(" Rezept nicht gefunden"));
    }

    public void deleteRecipe(String id){
        if (recipeRepository.existsById(id)) {
         recipeRepository.deleteById(id);
        }else{
            throw new RuntimeException("Recept nicht gefunden");
        }
     
    }

}
