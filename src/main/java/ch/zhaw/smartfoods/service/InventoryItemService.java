package ch.zhaw.smartfoods.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.enums.ItemStatus;
import ch.zhaw.smartfoods.enums.ProductUnit;
import ch.zhaw.smartfoods.model.InventoryItem.InventoryItem;
import ch.zhaw.smartfoods.model.InventoryItem.InventoryItemCreateDTO;
import ch.zhaw.smartfoods.repository.InventoryItemRepository;
import ch.zhaw.smartfoods.repository.StorageLocationRepository;

@Service
public class InventoryItemService {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    StorageLocationRepository storageLocationRepository;

    public List <InventoryItem>  createItems (InventoryItemCreateDTO dto){
        if( ! storageLocationRepository.findById(dto.getStorageLocationId()).isPresent()){
            throw new RuntimeException("Lagerort nicht gefunden"); }

        List<InventoryItem> created = new ArrayList<>();

        for(int i =0; i< dto.getQuantity(); i++){
        InventoryItem newItem = new InventoryItem(
            dto.getName(),
            dto.getUnit(),
            dto.getExpiryDate(),
            dto.getPurchasePrice(),
            dto.getStorageLocationId()
        );
        newItem.setPurchaseDate(LocalDate.now());
        newItem.setStatus(ItemStatus.ACTIVE);
        if (dto.getUnit()== ProductUnit.PIECE) {
            newItem.setTotalAmount(1.0);
            newItem.setRemainingAmount(1.0);
        }else{
            newItem.setRemainingAmount(dto.getTotalAmount());
            newItem.setTotalAmount(dto.getTotalAmount());
        }
            created.add(inventoryItemRepository.save(newItem));
        }
        return created;
    }

    public InventoryItem consumeItem(String id, Double amount){
        InventoryItem item = inventoryItemRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Item nicht gefunden"));

        if(item.getStatus()!= ItemStatus.ACTIVE){
            throw new RuntimeException("Item ist nicht mehr aktiv (wurde schon verbraucht/entsorgt");
        }
        if(item.getUnit()== ProductUnit.PIECE){
            item.setStatus(ItemStatus.CONSUMED);
        }else{
            var newRemaining= item.getRemainingAmount() - amount;
            if(newRemaining<= 0){
                item.setStatus(ItemStatus.CONSUMED);
                item.setRemainingAmount(0.0);
            }else{
                item.setRemainingAmount(newRemaining);
            }
        }
        return inventoryItemRepository.save(item);
    }



    public InventoryItem wasteItem (String id){
        InventoryItem item = inventoryItemRepository.findById(id)
        .orElseThrow(()-> new RuntimeException("Item nicht gefunden"));

        if( item.getStatus() == ItemStatus.CONSUMED || item.getStatus() == ItemStatus.WASTED){
           throw new RuntimeException("Already consumed or wasted");
        }
        item.setStatus(ItemStatus.WASTED);
        item.setRemainingAmount(0.0);
        return inventoryItemRepository.save(item);
    }
}
