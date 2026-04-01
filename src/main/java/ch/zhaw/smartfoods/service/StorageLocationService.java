package ch.zhaw.smartfoods.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;
import ch.zhaw.smartfoods.model.StorageLocation.StorageLocationCreateDTO;
import ch.zhaw.smartfoods.repository.StorageLocationRepository;

@Service
public class StorageLocationService {
    
    @Autowired
    StorageLocationRepository storageLocationRepository;

    public StorageLocation createLocation(StorageLocationCreateDTO dto){

        StorageLocation newSpace = new StorageLocation(
            dto.getName(),
            dto.getStorageType(),
            dto.getIcon()
        );
        return storageLocationRepository.save(newSpace);
    }
}
