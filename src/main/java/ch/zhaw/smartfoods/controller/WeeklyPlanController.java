package ch.zhaw.smartfoods.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ch.zhaw.smartfoods.model.Meal.Meal;
import ch.zhaw.smartfoods.model.Meal.WeeklyPlan;
import ch.zhaw.smartfoods.service.WeeklyPlanService;

@RestController
@RequestMapping("/api")
public class WeeklyPlanController {

    @Autowired
    WeeklyPlanService weeklyPlanService;

    @GetMapping("/weekly-plan")
    public ResponseEntity<WeeklyPlan> getWeeklyPlan() {
        try {
            return ResponseEntity.ok(weeklyPlanService.getOrCreateWeeklyPlan());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/weekly-plan/{day}")
    public ResponseEntity<WeeklyPlan> addMealToDay(
            @PathVariable String day,
            @RequestBody Meal meal) {
        try {
            return ResponseEntity.ok(weeklyPlanService.addMealToDay(day, meal));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/weekly-plan/{day}/{mealIndex}")
    public ResponseEntity<WeeklyPlan> removeMealFromDay(
            @PathVariable String day,
            @PathVariable int mealIndex) {
        try {
            return ResponseEntity.ok(weeklyPlanService.removeMealFromDay(day, mealIndex));
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/weekly-plan/{day}")
    public ResponseEntity<Void> clearDay(@PathVariable String day) {
        try {
            weeklyPlanService.clearDay(day);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}