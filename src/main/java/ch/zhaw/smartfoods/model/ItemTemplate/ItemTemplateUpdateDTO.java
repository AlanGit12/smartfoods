package ch.zhaw.smartfoods.model.ItemTemplate;

import ch.zhaw.smartfoods.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class ItemTemplateUpdateDTO {
    private String name;
    private ProductUnit defaultUnit;
    private Double defaultPrice;
    private Double defaultAmount;
}
