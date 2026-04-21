package ch.zhaw.smartfoods.model.Recipe;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecipeCreateDTO {

    private String name;
    private String description;
    private List<Ingredient> ingredients;
    
}
