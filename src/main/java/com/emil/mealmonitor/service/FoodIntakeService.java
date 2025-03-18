package com.emil.mealmonitor.service;

import com.emil.mealmonitor.model.entity.FoodIntake;
import com.emil.mealmonitor.model.entity.Meal;
import com.emil.mealmonitor.repository.FoodIntakeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FoodIntakeService {
    private final FoodIntakeRepository foodIntakeRepository;

    public void addFoodIntake (FoodIntake intake){
        foodIntakeRepository.save(intake);
    }


    public Map<String, Object> getDailyReport(Long userId) {
        List<FoodIntake> intakes = foodIntakeRepository.findByUserIdAndDate(userId, LocalDate.now());

        int totalCalories = intakes.stream()
                .flatMap(i -> i.getMeals().stream())
                .mapToInt(Meal::getCalories)
                .sum();

        boolean withinLimit = totalCalories <= intakes.get(0).getUser().getDailyCalories();

        return Map.of("totalCalories", totalCalories, "withinLimit", withinLimit);
    }
}

