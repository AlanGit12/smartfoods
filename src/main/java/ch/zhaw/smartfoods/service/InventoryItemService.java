package ch.zhaw.smartfoods.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.enums.ItemStatus;
import ch.zhaw.smartfoods.model.InventoryItem;
import ch.zhaw.smartfoods.model.InventoryItemCreateDTO;
import ch.zhaw.smartfoods.repository.InventoryItemRepository;
import ch.zhaw.smartfoods.repository.StorageLocationRepository;

@Service
public class InventoryItemService {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    StorageLocationRepository storageLocationRepository;

    public InventoryItem createItem (InventoryItemCreateDTO dto){

        if( ! storageLocationRepository.findById(dto.getStorageLocationId()).isPresent()){
            throw new RuntimeException("Lagerort nicht gefunden");
        }
        InventoryItem newItem = new InventoryItem(
            dto.getName(),
            dto.getQuantity(),
            dto.getExpiryDate(),
            dto.getPrice(),
            dto.getStorageLocationId()
        );
        newItem.setPurchaseDate(LocalDate.now());
        newItem.setStatus(ItemStatus.ACTIVE);
        return inventoryItemRepository.save(newItem);
        
    }
    
}
