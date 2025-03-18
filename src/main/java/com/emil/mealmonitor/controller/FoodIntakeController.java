package com.emil.mealmonitor.controller;

import com.emil.mealmonitor.model.entity.FoodIntake;
import com.emil.mealmonitor.service.FoodIntakeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/food-intake")

public class FoodIntakeController {
    private final FoodIntakeService foodIntakeService;

    public FoodIntakeController(FoodIntakeService foodIntakeService) {
        this.foodIntakeService = foodIntakeService;
    }

    @PostMapping
    public ResponseEntity<Void> addFoodIntake(@Valid @RequestBody FoodIntake intake) {
        foodIntakeService.addFoodIntake(intake);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/daily-report/{userId}")
    public ResponseEntity<Map<String, Object>> getDailyReport(@PathVariable Long userId) {
       var result = foodIntakeService.getDailyReport(userId);
       return ResponseEntity.ok(result);
    }
}

