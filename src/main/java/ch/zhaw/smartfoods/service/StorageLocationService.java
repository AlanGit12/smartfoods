package ch.zhaw.smartfoods.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;
import ch.zhaw.smartfoods.model.StorageLocation.StorageLocationCreateDTO;
import ch.zhaw.smartfoods.repository.StorageLocationRepository;

@Service
public class StorageLocationService {

    @Autowired
    StorageLocationRepository storageLocationRepository;

    @Autowired
    UserService userService;

    public StorageLocation createLocation(StorageLocationCreateDTO dto) {

        String userId = userService.getUserId();

        StorageLocation newSpace = new StorageLocation(
                dto.getName(),
                dto.getStorageType(),
                dto.getIcon());
        newSpace.setUserId(userId);
        return storageLocationRepository.save(newSpace);
    }

    public List<StorageLocation> getAllLocations() {
        String userId = userService.getUserId();
        return storageLocationRepository.findByUserId(userId);
    }

    public StorageLocation getLocationById(String id) {
        return storageLocationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lagerort nicht gefunden"));
    }
}
