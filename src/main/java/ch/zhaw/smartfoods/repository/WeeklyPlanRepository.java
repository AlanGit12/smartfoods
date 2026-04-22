package ch.zhaw.smartfoods.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import ch.zhaw.smartfoods.model.Meal.WeeklyPlan;

public interface WeeklyPlanRepository extends MongoRepository<WeeklyPlan, String> {
     Optional<WeeklyPlan> findByUserId(String userId);
}
