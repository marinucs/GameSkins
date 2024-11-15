package com.itacademy.gameskins.exception;

import com.itacademy.gameskins.model.Color;

import java.util.Arrays;

public class InvalidColorException extends RuntimeException {
    public InvalidColorException(String colorValue, Throwable cause) {
        super("Color value <" + colorValue + "> is not valid | Available colors: " + Arrays.toString(Color.values()));
    }
}