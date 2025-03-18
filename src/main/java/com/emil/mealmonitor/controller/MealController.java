package com.emil.mealmonitor.controller;

import com.emil.mealmonitor.model.entity.Meal;
import com.emil.mealmonitor.service.MealService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/meals")
@RequiredArgsConstructor
public class MealController {
    private final MealService mealService;

    @PostMapping("/crate")
    public ResponseEntity<Void> createMeal(@Valid @RequestBody Meal meal) {
        mealService.createMeal(meal);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public List<Meal> getAllMeals() {
        return mealService.getAllMeal();
    }
}

