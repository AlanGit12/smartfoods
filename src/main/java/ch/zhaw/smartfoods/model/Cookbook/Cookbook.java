package ch.zhaw.smartfoods.model.Cookbook;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.zhaw.smartfoods.model.Recipe.UserRecipe;
import lombok.Getter;
import lombok.NoArgsConstructor;

import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
@Document("Cookbook")
public class Cookbook {

    @Id
    private String id;
    private String userId;
    private List<String> savedRecipeIds = new ArrayList<>();
    private List<UserRecipe> userRecipes = new ArrayList<>();
}
