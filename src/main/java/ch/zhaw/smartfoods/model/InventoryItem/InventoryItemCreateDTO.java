package ch.zhaw.smartfoods.model.InventoryItem;

import java.time.LocalDate;

import ch.zhaw.smartfoods.enums.ProductUnit;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InventoryItemCreateDTO {

    private String name;
    private ProductUnit unit;
    private Double totalAmount;
    private int quantity;
    private LocalDate expiryDate;
    private Double purchasePrice;
    private String storageLocationId;
}
