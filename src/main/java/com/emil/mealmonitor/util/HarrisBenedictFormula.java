package com.emil.mealmonitor.util;


import com.emil.mealmonitor.model.entity.User;

public class HarrisBenedictFormula {
    public static double calculate(User user) {
        double bmr = 10 * user.getWeight() + 6.25 * user.getHeight() - 5 * user.getAge();
        return switch (user.getGoal()) {
            case WEIGHT_LOSS -> bmr * 1.2 - 500;
            case MAINTENANCE -> bmr * 1.55;
            case WEIGHT_GAIN -> bmr * 1.8 + 500;
        };
    }
}
