package com.emil.mealmonitor.service;

import com.emil.mealmonitor.exception.NoFoodIntakeDataException;
import com.emil.mealmonitor.exception.UserNotFoundException;
import com.emil.mealmonitor.model.entity.FoodIntake;
import com.emil.mealmonitor.model.entity.Meal;
import com.emil.mealmonitor.model.entity.User;
import com.emil.mealmonitor.repository.FoodIntakeRepository;
import com.emil.mealmonitor.repository.MealRepository;
import com.emil.mealmonitor.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FoodIntakeServiceTest {

    @Mock
    private FoodIntakeRepository foodIntakeRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MealRepository mealRepository;

    @InjectMocks
    private FoodIntakeService foodIntakeService;

    private User testUser;
    private Meal testMeal;
    private FoodIntake testFoodIntake;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testUser = new User(1L, "Emil Bagirov", "emil_bgr@mail.ru", 23, 70.0, 183.0, null, 2000.0);
        testMeal = new Meal(1L, "Salad", 200, 10.0, 5.0, 30.0);
        testFoodIntake = new FoodIntake(1L, testUser, List.of(testMeal), LocalDate.now());
    }

    @Test
    void addFoodIntake_ShouldReturnFoodIntake_WhenMealsExist() {

        List<Long> mealIds = List.of(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealRepository.findAllById(mealIds)).thenReturn(List.of(testMeal));
        when(foodIntakeRepository.save(any(FoodIntake.class))).thenReturn(testFoodIntake);


        FoodIntake result = foodIntakeService.addFoodIntake(1L, mealIds);


        assertNotNull(result);
        assertEquals(testUser.getId(), result.getUser().getId());
        assertEquals(1, result.getMeals().size());
        assertEquals(testMeal.getName(), result.getMeals().get(0).getName());
        verify(foodIntakeRepository, times(1)).save(any(FoodIntake.class));
    }

    @Test
    void addFoodIntake_ShouldThrowUserNotFoundException_WhenUserNotFound() {

        List<Long> mealIds = List.of(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.empty());


        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            foodIntakeService.addFoodIntake(1L, mealIds);
        });

        assertEquals("User with ID 1 not found", exception.getMessage());
    }

    @Test
    void addFoodIntake_ShouldThrowNoSuchElementException_WhenMealsNotFound() {

        List<Long> mealIds = List.of(1L);
        when(userRepository.findById(1L)).thenReturn(Optional.of(testUser));
        when(mealRepository.findAllById(mealIds)).thenReturn(List.of());


        assertThrows(NoSuchElementException.class, () -> {
            foodIntakeService.addFoodIntake(1L, mealIds);
        });
    }

    @Test
    void getDailyReport_ShouldReturnReport_WhenFoodIntakesExist() {

        when(foodIntakeRepository.findByUserIdAndDate(1L, LocalDate.now())).thenReturn(List.of(testFoodIntake));


        Map<String, Object> result = foodIntakeService.getDailyReport(1L);


        assertNotNull(result);
        assertEquals(200, result.get("totalCalories"));
        assertEquals(2000.0, result.get("dailyCaloriesLimit"));
        assertTrue((Boolean) result.get("withinLimit"));
    }

    @Test
    void getDailyReport_ShouldThrowNoFoodIntakeDataException_WhenNoFoodIntakesExist() {

        when(foodIntakeRepository.findByUserIdAndDate(1L, LocalDate.now())).thenReturn(List.of());


        NoFoodIntakeDataException exception = assertThrows(NoFoodIntakeDataException.class, () -> {
            foodIntakeService.getDailyReport(1L);
        });

        assertEquals("No food intake data found for user ID 1", exception.getMessage());
    }
}

