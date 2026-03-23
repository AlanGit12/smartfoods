package ch.zhaw.smartfoods.model;

import java.time.LocalDate;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class InventoryItemCreateDTO {

    private String name;
    private int quantity;
    private LocalDate expiryDate;
    private double price;
    private String storageLocationId;
}
