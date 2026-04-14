package ch.zhaw.smartfoods.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplate;
import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplateCreateDTO;
import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplateUpdateDTO;
import ch.zhaw.smartfoods.repository.ItemTemplateRepository;
import ch.zhaw.smartfoods.service.ItemTemplateService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class ItemTemplateController {

    @Autowired
    ItemTemplateRepository itemTemplateRepository;

    @Autowired
    ItemTemplateService itemTemplateService;

    @PostMapping("/template")
    public ResponseEntity<ItemTemplate> createItemTemplate(@RequestBody ItemTemplateCreateDTO dto) {
        try {
            ItemTemplate saved = itemTemplateService.createTemplate(dto);
            return new ResponseEntity<>(saved, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/template")
    public ResponseEntity<List<ItemTemplate>> getItemTemplates() {
        try {
            return ResponseEntity.ok(itemTemplateService.getAllTemplates());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<ItemTemplate> getItemTemplateById(@PathVariable String id) {
        try {
            ItemTemplate template = itemTemplateService.getTemplateById(id);
            return ResponseEntity.ok(template);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/template/{id}")
    public ResponseEntity<Void> deleteItemTemplateById(@PathVariable String id) {
        try {
            itemTemplateService.deleteTemplate(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PatchMapping("/template/{id}")
    public ResponseEntity<ItemTemplate> updateTemplate(@PathVariable String id,
            @RequestBody ItemTemplateUpdateDTO dto) {

        try {
            ItemTemplate updated = itemTemplateService.updateTemplate(id, dto);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }

    }

}
