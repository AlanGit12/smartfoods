package ch.zhaw.smartfoods.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.InventoryItem;
import ch.zhaw.smartfoods.model.InventoryItemCreateDTO;
import ch.zhaw.smartfoods.repository.InventoryItemRepository;
import ch.zhaw.smartfoods.service.InventoryItemService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api")
public class InventoryItemController {

    @Autowired
    InventoryItemRepository inventoryItemRepository;

    @Autowired
    InventoryItemService inventoryItemService;

    @PostMapping("/inventory")
    public ResponseEntity<InventoryItem> createItem(@RequestBody InventoryItemCreateDTO dto) {
        try {
            InventoryItem saved = inventoryItemService.createItem(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/inventory")
    public ResponseEntity<List<InventoryItem>> getAllItems() {
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
}
