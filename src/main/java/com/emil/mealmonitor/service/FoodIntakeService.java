package com.emil.mealmonitor.service;

import com.emil.mealmonitor.exception.NoFoodIntakeDataException;
import com.emil.mealmonitor.exception.UserNotFoundException;
import com.emil.mealmonitor.model.entity.FoodIntake;
import com.emil.mealmonitor.model.entity.Meal;
import com.emil.mealmonitor.model.entity.User;
import com.emil.mealmonitor.repository.FoodIntakeRepository;
import com.emil.mealmonitor.repository.MealRepository;
import com.emil.mealmonitor.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FoodIntakeService {
    private final FoodIntakeRepository foodIntakeRepository;
    private final UserRepository userRepository;
    private final MealRepository mealRepository;

    public FoodIntake addFoodIntake(Long userId, List<Long> mealIds) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        List<Meal> meals = mealRepository.findAllById(mealIds);
        if (meals.isEmpty()) {
            throw new NoSuchElementException("Meals not found");
        }

        FoodIntake intake = new FoodIntake();
        intake.setUser(user);
        intake.setMeals(meals);
        intake.setDate(LocalDate.now());

        return foodIntakeRepository.save(intake);
    }


    public Map<String, Object> getDailyReport(Long userId) {
        List<FoodIntake> intakes = foodIntakeRepository.findByUserIdAndDate(userId, LocalDate.now());

        if (intakes.isEmpty()) {
            throw new NoFoodIntakeDataException(userId);
        }

        int totalCalories = intakes.stream()
                .flatMap(i -> i.getMeals().stream())
                .mapToInt(Meal::getCalories)
                .sum();

        double dailyCaloriesLimit = intakes.get(0).getUser().getDailyCalories();

        boolean withinLimit = totalCalories <= dailyCaloriesLimit;

        return Map.of(
                "totalCalories", totalCalories,
                "dailyCaloriesLimit", dailyCaloriesLimit,
                "withinLimit", withinLimit
        );
    }


}

