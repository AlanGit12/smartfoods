package ch.zhaw.smartfoods.model.Recipe;

import ch.zhaw.smartfoods.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class Ingredient {

    @NonNull
    private String name;
    @NonNull
    private Double amount;
    @NonNull
    private ProductUnit unit;
}
