package ch.zhaw.smartfoods.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;

public interface StorageLocationRepository extends MongoRepository <StorageLocation, String> {
    
}
