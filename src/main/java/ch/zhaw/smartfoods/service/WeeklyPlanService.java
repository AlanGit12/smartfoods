package ch.zhaw.smartfoods.service;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.zhaw.smartfoods.model.Meal.DayEntry;
import ch.zhaw.smartfoods.model.Meal.Meal;
import ch.zhaw.smartfoods.model.Meal.WeeklyPlan;
import ch.zhaw.smartfoods.repository.WeeklyPlanRepository;

@Service
public class WeeklyPlanService {

    @Autowired
    WeeklyPlanRepository weeklyPlanRepository;

    @Autowired
    UserService userService;

    public WeeklyPlan getOrCreateWeeklyPlan() {
        String userId = userService.getUserId();

        Optional<WeeklyPlan> existing = weeklyPlanRepository.findByUserId(userId);
        if (existing.isPresent()) {
            return existing.get();
        }

        WeeklyPlan plan = new WeeklyPlan();
        plan.setUserId(userId);
        plan.setWeek(new ArrayList<>());
        return weeklyPlanRepository.save(plan);
    }

    public WeeklyPlan addMealToDay(String day, Meal meal) {
        WeeklyPlan plan = getOrCreateWeeklyPlan();

        // Schau ob Tag bereits existiert
        Optional<DayEntry> existingDay = plan.getWeek().stream()
                .filter(d -> d.getDay().equals(day))
                .findFirst();

        if (existingDay.isPresent()) {
            // Tag existiert → Mahlzeit hinzufügen
            existingDay.get().getMeals().add(meal);
        } else {
            // Tag existiert noch nicht → neu erstellen
            DayEntry newDay = new DayEntry(day);

            newDay.getMeals().add(meal);
            plan.getWeek().add(newDay);
        }
        return weeklyPlanRepository.save(plan);
    }

    public WeeklyPlan removeMealFromDay(String day, int mealIndex) {
        WeeklyPlan plan = getOrCreateWeeklyPlan();

        plan.getWeek().stream()
                .filter(d -> d.getDay().equals(day))
                .findFirst()
                .ifPresent(d -> d.getMeals().remove(mealIndex));

        return weeklyPlanRepository.save(plan);
    }

    public WeeklyPlan clearDay(String day) {
        WeeklyPlan plan = getOrCreateWeeklyPlan();
        plan.getWeek().removeIf(d -> d.getDay().equals(day));
        return weeklyPlanRepository.save(plan);
    }
}
