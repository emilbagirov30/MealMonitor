package com.emil.mealmonitor.model.entity;

import com.emil.mealmonitor.model.Goal;
import com.emil.mealmonitor.util.HarrisBenedictFormula;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank
    @Column(name = "name")
    private String name;

    @Email
    @NotBlank
    @Column(name = "email")
    private String email;

    @Min(10) @Max(100)
    @Column(name = "age")
    private int age;

    @Min(30) @Max(300)
    @Column(name = "weight")
    private double weight;

    @Min(90) @Max(240)
    @Column(name = "height")
    private double height;

    @Enumerated(EnumType.STRING)
    @Column(name = "goal")
    private Goal goal;

    private double dailyCalories;

    @PostPersist
    @PostUpdate
    private void calculateDailyCalories() {
        this.dailyCalories = HarrisBenedictFormula.calculate(this);
    }
}

