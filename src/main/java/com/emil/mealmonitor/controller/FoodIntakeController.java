package com.emil.mealmonitor.controller;

import com.emil.mealmonitor.model.entity.FoodIntake;
import com.emil.mealmonitor.service.FoodIntakeService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/food-intake")

public class FoodIntakeController {
    private final FoodIntakeService foodIntakeService;

    public FoodIntakeController(FoodIntakeService foodIntakeService) {
        this.foodIntakeService = foodIntakeService;
    }

    @PostMapping("/create")
    public ResponseEntity<Map<String, Object>> addFoodIntake(@RequestBody Map<String, Object> request) {
        Long userId = ((Number) request.get("userId")).longValue();
        List<Long> mealIds = (List<Long>) request.get("mealIds");

        FoodIntake intake = foodIntakeService.addFoodIntake(userId, mealIds);

        return ResponseEntity.ok(Map.of(
                "message", "Food intake added successfully",
                "foodIntakeId", intake.getId()
        ));
    }


    @GetMapping("/daily-report/{userId}")
    public ResponseEntity<Map<String, Object>> getDailyReport(@PathVariable Long userId) {
       var result = foodIntakeService.getDailyReport(userId);
       return ResponseEntity.ok(result);
    }
}

