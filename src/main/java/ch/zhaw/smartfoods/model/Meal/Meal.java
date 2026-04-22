package ch.zhaw.smartfoods.model.Meal;


import ch.zhaw.smartfoods.enums.MealType;
import ch.zhaw.smartfoods.enums.RecipeSource;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Meal {
    
    @NonNull
    private String recipeId;
    @NonNull
    private RecipeSource recipeSource;
    @NonNull
    private MealType mealType;
}
