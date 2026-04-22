package ch.zhaw.smartfoods.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.Cookbook.Cookbook;

public interface CookbookRepository extends MongoRepository <Cookbook, String> {

    Optional<Cookbook> findByUserId(String userId);
    
}
