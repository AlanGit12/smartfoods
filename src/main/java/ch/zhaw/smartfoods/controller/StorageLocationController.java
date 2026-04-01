package ch.zhaw.smartfoods.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.StorageLocation.StorageLocation;
import ch.zhaw.smartfoods.model.StorageLocation.StorageLocationCreateDTO;
import ch.zhaw.smartfoods.repository.StorageLocationRepository;
import ch.zhaw.smartfoods.service.StorageLocationService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class StorageLocationController {

    @Autowired
    StorageLocationRepository storageLocationRepository;

    @Autowired
    StorageLocationService storageLocationService;

    @PostMapping("/storage")
    public ResponseEntity<StorageLocation> createStorageLocation (@RequestBody StorageLocationCreateDTO dto) {

        try{
            StorageLocation saved = storageLocationService.createLocation(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        }catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }  

    @GetMapping("/storage")
    public ResponseEntity<List<StorageLocation>> getStorageLocation() {
        return ResponseEntity.ok(storageLocationRepository.findAll());
    }
    
    @GetMapping("/storage/{id}")
    public ResponseEntity<StorageLocation> getStorageLocationById(@PathVariable String id){
         Optional<StorageLocation> place = storageLocationRepository.findById(id);
         if( place.isPresent()){
            return ResponseEntity.ok(place.get());
         }
         return ResponseEntity.notFound().build();
    }


}
