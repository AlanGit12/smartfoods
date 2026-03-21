package ch.zhaw.smartfoods.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import ch.zhaw.smartfoods.enums.ItemStatus;
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
    private Integer quantity;
    private LocalDate purchaseDate;
    @NonNull
    private LocalDate expiryDate;
    @NonNull
    private Double price;
    private ItemStatus status = ItemStatus.ACTIVE;
}
