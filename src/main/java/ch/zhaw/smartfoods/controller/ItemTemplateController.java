package ch.zhaw.smartfoods.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplate;
import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplateCreateDTO;
import ch.zhaw.smartfoods.repository.ItemTemplateRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RestController
@RequestMapping("/api")
public class ItemTemplateController {

    @Autowired
    ItemTemplateRepository  itemTemplateRepository;

    @PostMapping("/template/")
    public ResponseEntity<ItemTemplate> createItemTemplate(@RequestBody ItemTemplateCreateDTO dto) {
  
        ItemTemplate template = new ItemTemplate(
            dto.getName(),
            dto.getDefaultUnit(),
            dto.getDefaultPrice(),
            dto.getDefaultAmount()
        );
        return ResponseEntity.ok(itemTemplateRepository.save(template));
    }
    

    @GetMapping("/template")
    public ResponseEntity<List<ItemTemplate>> getItemTemplates(){
        return ResponseEntity.ok(itemTemplateRepository.findAll());
    }

    @GetMapping("/template/{id}")
    public ResponseEntity<ItemTemplate> getItemTemplateById(@PathVariable String id) {
        
        Optional <ItemTemplate> template=  itemTemplateRepository.findById(id);
        if( template.isPresent()){
            return ResponseEntity.ok(template.get());
         }
         return ResponseEntity.notFound().build();
        
    }
}
