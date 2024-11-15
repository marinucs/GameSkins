package com.itacademy.gameskins.exception;

public class SkinAlreadyPurchasedException extends ApplicationException {
    public SkinAlreadyPurchasedException(Long id) {
        super("Skin with ID " + id + " has already been purchased.");
    }
}
