package ch.zhaw.smartfoods.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.InventoryItem;

public interface InventoryItemRepository extends MongoRepository <InventoryItem, String>{
    
}
