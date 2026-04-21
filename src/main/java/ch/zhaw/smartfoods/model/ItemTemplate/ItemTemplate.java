package ch.zhaw.smartfoods.model.ItemTemplate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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
@Document("ItemTemplate")
public class ItemTemplate {
    
    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private ProductUnit defaultUnit;
    @NonNull
    private Double defaultPrice;
    @NonNull
    private Double defaultAmount;
    private String userId;
}
