package com.itacademy.gameskins.exception;

import com.itacademy.gameskins.model.Color;

import java.util.Arrays;

public class InvalidColorException extends RuntimeException {
    public InvalidColorException(String colorValue) {
        super(String.format("Color value <%s> is not valid | Available colors: %s",
                colorValue,
                Arrays.toString(Color.values())));
    }
}