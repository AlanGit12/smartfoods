package ch.zhaw.smartfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;
import ch.zhaw.smartfoods.model.StorageLocation.StorageLocationCreateDTO;
import ch.zhaw.smartfoods.service.StorageLocationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class StorageLocationController {


    @Autowired
    StorageLocationService storageLocationService;

    @PostMapping("/storage-locations")
    public ResponseEntity<StorageLocation> createStorageLocation(@RequestBody StorageLocationCreateDTO dto) {

        try {
            StorageLocation saved = storageLocationService.createLocation(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/storage-locations")
    public ResponseEntity<List<StorageLocation>> getStorageLocation() {
        return ResponseEntity.ok(storageLocationService.getAllLocations());
    }

    @GetMapping("/storage-locations/{id}")
    public ResponseEntity<StorageLocation> getStorageLocationById(@PathVariable String id) {
     
        try{
            StorageLocation loc = storageLocationService.getLocationById(id);
            return ResponseEntity.ok(loc);
        } catch( RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

}
