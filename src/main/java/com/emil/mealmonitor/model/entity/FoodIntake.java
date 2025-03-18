package com.emil.mealmonitor.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "food_intake")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodIntake {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    @ManyToMany
    @JoinTable(
            name = "food_intake_meals",
            joinColumns = @JoinColumn(name = "food_intake_id"),
            inverseJoinColumns = @JoinColumn(name = "meal_id")
    )
    private List<Meal> meals;

    private LocalDate date;

    @PrePersist
    private void setDate() {
        this.date = LocalDate.now();
    }
}
