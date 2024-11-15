package com.itacademy.gameskins.model;

import com.itacademy.gameskins.exception.InvalidColorException;

public enum Color {
    RED,
    BLUE,
    GREEN,
    YELLOW,
    BLACK,
    WHITE,
    ORANGE,
    METALLIC,
    GOLDEN,
    PURPLE;

    public static Color fromString(String color) {
        if (color == null) {
            throw new IllegalArgumentException("Color cannot be null");
        }
        try {
            return Color.valueOf(color.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidColorException(color);
        }
    }
}
