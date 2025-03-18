package com.emil.mealmonitor.service;


import com.emil.mealmonitor.model.entity.Meal;
import com.emil.mealmonitor.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealService {
    private final MealRepository mealRepository;


    public void createMeal (Meal meal){
        mealRepository.save(meal);
    }

    public List<Meal> getAllMeal (){
        return mealRepository.findAll();
    }

}
