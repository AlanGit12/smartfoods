package ch.zhaw.smartfoods.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.InventoryItem;
import java.util.List;


public interface InventoryItemRepository extends MongoRepository <InventoryItem, String>{
    List<InventoryItem> findByStorageLocationId(String storageLocationId);
}
