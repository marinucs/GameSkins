package com.itacademy.gameskins.exception;

public class SkinNotFoundException extends ApplicationException {
    public SkinNotFoundException(Long id) {
        super("Skin with ID " + id + " not found.");
    }
}
