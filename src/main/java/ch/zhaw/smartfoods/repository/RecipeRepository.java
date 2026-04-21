package ch.zhaw.smartfoods.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.Recipe.Recipe;

public interface RecipeRepository extends MongoRepository <Recipe, String>{
    
}
