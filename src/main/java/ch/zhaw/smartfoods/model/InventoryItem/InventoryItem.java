package ch.zhaw.smartfoods.model.InventoryItem;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.zhaw.smartfoods.enums.ItemStatus;
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
@Document("InventoryItem")
public class InventoryItem {

    @Id
    private String id;
    @NonNull
    private String name;
    @NonNull
    private ProductUnit unit;
    private Double totalAmount;
    private Double remainingAmount;
    private LocalDate purchaseDate;
    @NonNull
    private LocalDate expiryDate;
    @NonNull
    private Double purchesePrice;
    private ItemStatus status;
    @NonNull
    private String storageLocationId; 

}
