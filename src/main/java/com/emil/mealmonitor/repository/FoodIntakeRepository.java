package com.emil.mealmonitor.repository;

import com.emil.mealmonitor.model.entity.FoodIntake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface FoodIntakeRepository extends JpaRepository<FoodIntake, Long> {
    List<FoodIntake> findByUserIdAndDate(Long userId, LocalDate date);
}