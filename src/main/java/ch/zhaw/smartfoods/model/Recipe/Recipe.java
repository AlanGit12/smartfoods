package ch.zhaw.smartfoods.model.Recipe;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
@Document("Recipe")
public class Recipe {
    
    @Id
    private String id;

    @NonNull
    private String name;
    @NonNull
    private String description;
    @NonNull
    private List<Ingredient> ingredient;
    private String createdBy;
}
