package ch.zhaw.smartfoods.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;

public interface StorageLocationRepository extends MongoRepository <StorageLocation, String> {

        List<StorageLocation> findByUserId(String userId);

    
}
