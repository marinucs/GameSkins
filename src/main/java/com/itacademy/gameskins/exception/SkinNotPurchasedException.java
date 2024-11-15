package com.itacademy.gameskins.exception;

public class SkinNotPurchasedException extends ApplicationException {
    public SkinNotPurchasedException(Long id) {
        super("Skin with ID " + id + " has not been purchased and cannot be modified or deleted.");
    }
}