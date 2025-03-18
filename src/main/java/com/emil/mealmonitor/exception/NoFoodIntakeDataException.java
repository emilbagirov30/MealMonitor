package com.emil.mealmonitor.exception;

public class NoFoodIntakeDataException extends RuntimeException {
    public NoFoodIntakeDataException(Long userId) {
        super("Нет данных о приеме пищи для пользователя с ID: " + userId);
    }
}
