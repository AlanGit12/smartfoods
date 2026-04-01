package ch.zhaw.smartfoods.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplate;

public interface ItemTemplateRepository extends MongoRepository<ItemTemplate, String>{
    
}
