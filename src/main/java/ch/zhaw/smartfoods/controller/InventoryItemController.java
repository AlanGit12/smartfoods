package ch.zhaw.smartfoods.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.InventoryItem.InventoryItem;
import ch.zhaw.smartfoods.model.InventoryItem.InventoryItemCreateDTO;
import ch.zhaw.smartfoods.repository.InventoryItemRepository;
import ch.zhaw.smartfoods.service.InventoryItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api")
public class InventoryItemController {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    InventoryItemService inventoryItemService;

    @PostMapping("/inventory")
    public ResponseEntity<List<InventoryItem>> createItems(@RequestBody InventoryItemCreateDTO dto) {
        try {
            List<InventoryItem> saved = inventoryItemService.createItems(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryItem>> getAllItems(
            @RequestParam(required = false) String storageLocationId) {

        if (storageLocationId != null) {
            return ResponseEntity.ok(
                    inventoryItemRepository.findByStorageLocationId(storageLocationId));
        }
        return ResponseEntity.ok(inventoryItemRepository.findAll());
    }

    @GetMapping("/inventory/{id}")
    public ResponseEntity<InventoryItem> getItemById(@PathVariable String id) {
        Optional<InventoryItem> item = inventoryItemRepository.findById(id);
        if (item.isPresent()) {
            return ResponseEntity.ok(item.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PatchMapping("/inventory/{id}/consume")
    public ResponseEntity<InventoryItem> consumeItem(
            @PathVariable String id,
            @RequestParam Double amount) {
        try {
            InventoryItem updated = inventoryItemService.consumeItem(id, amount);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/inventory/{id}/waste")
    public ResponseEntity<InventoryItem> wasteItem(
        @PathVariable String id){
            try{
                InventoryItem updated = inventoryItemService.wasteItem(id);
                return ResponseEntity.ok(updated);
            }catch (RuntimeException e){
                return ResponseEntity.notFound().build();
            }
            }
        }
