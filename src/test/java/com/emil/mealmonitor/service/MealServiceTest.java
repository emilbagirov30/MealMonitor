package com.emil.mealmonitor.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import com.emil.mealmonitor.model.entity.Meal;

import com.emil.mealmonitor.repository.MealRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

public class MealServiceTest {

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private MealService mealService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMeal_ShouldSaveMeal() {

        Meal meal = new Meal(1L, "Salad", 150, 5, 10, 20);
        when(mealRepository.save(meal)).thenReturn(meal);


        mealService.createMeal(meal);


        verify(mealRepository, times(1)).save(meal);
    }

    @Test
    void getAllMeal_ShouldReturnListOfMeals() {

        Meal meal1 = new Meal(1L, "Salad", 150, 5, 10, 20);
        Meal meal2 = new Meal(2L, "Pasta", 300, 10, 20, 40);
        List<Meal> expectedMeals = Arrays.asList(meal1, meal2);
        when(mealRepository.findAll()).thenReturn(expectedMeals);


        List<Meal> actualMeals = mealService.getAllMeal();


        assertNotNull(actualMeals);
        assertEquals(2, actualMeals.size());
        assertTrue(actualMeals.contains(meal1));
        assertTrue(actualMeals.contains(meal2));
    }
}
