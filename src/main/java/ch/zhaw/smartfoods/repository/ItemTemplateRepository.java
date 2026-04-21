package ch.zhaw.smartfoods.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.ItemTemplate.ItemTemplate;
import java.util.List;


public interface ItemTemplateRepository extends MongoRepository<ItemTemplate, String>{
    Optional<ItemTemplate> findByName(String name);
    List<ItemTemplate> findByUserId(String userId);
}
