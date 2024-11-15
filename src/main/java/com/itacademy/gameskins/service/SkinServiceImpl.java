package com.itacademy.gameskins.service;

import com.itacademy.gameskins.exception.InvalidColorException;
import com.itacademy.gameskins.model.Color;
import com.itacademy.gameskins.model.Skin;
import com.itacademy.gameskins.repository.PurchasedSkinRepository;
import com.itacademy.gameskins.util.SkinLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// import org.slf4j.Logger;
// import org.slf4j.LoggerFactory;

import com.itacademy.gameskins.exception.SkinAlreadyPurchasedException;
import com.itacademy.gameskins.exception.SkinNotFoundException;
import com.itacademy.gameskins.exception.SkinNotPurchasedException;


@Service
public class SkinServiceImpl implements SkinService {

    // private static final Logger logger = LoggerFactory.getLogger(SkinServiceImpl.class);
    private final SkinLoader skinLoader;
    private final PurchasedSkinRepository purchasedSkinRepository;

    @Autowired
    public SkinServiceImpl(SkinLoader skinLoader, PurchasedSkinRepository purchasedSkinRepository) {
        this.skinLoader = skinLoader;
        this.purchasedSkinRepository = purchasedSkinRepository;
    }

    @Override
    public List<Skin> getAvailableSkins() {
        return skinLoader.getAvailableSkins();
    }

    @Override
    public List<Skin> getMySkins() {
        return purchasedSkinRepository.findAll();
    }

    @Override
    public Skin buySkin(Long id) {
        Skin skinToBuy = skinLoader.getSkinById(id);
        if (skinToBuy == null) {
            // logger.warn("Skin with ID {} not found", id);
            throw new SkinNotFoundException(id);
        }

        if (purchasedSkinRepository.findById(id).isPresent()) {
            // logger.warn("Skin with ID {} already purchased", id);
            throw new SkinAlreadyPurchasedException(id);
        }

        // logger.info("Skin with ID {} purchased", id);
        return purchasedSkinRepository.save(skinToBuy);
    }

    @Override
    public Skin getSkinById(Long id) throws SkinNotFoundException {
        Skin availableSkin = skinLoader.getSkinById(id);
        if (availableSkin == null) {
            // logger.error("Skin with ID {} not found in available skins", id);
            throw new SkinNotFoundException(id);
        }
        return availableSkin;
    }

    @Override
    public Skin updateSkinColor(Long id, String colorValue) {
        // Convert the String value to Color
        Color color;
        try {
            color = Color.fromString(colorValue);
        } catch (IllegalArgumentException e) {
            // logger.error("Color {} not valid", colorValue);
            throw new InvalidColorException(colorValue, e);
        }

        // Retrieve the skin
        Optional<Skin> optionalSkin = purchasedSkinRepository.findById(id);
        if (optionalSkin.isEmpty()) {
            // logger.error("Skin with id {} not purchased", id);
            throw new SkinNotPurchasedException(id);
        }

        // Update the skin's color
        Skin skin = optionalSkin.get();
        skin.setColor(color);
        // logger.info("Color of skin with id {} updated to {}", id, color);
        return purchasedSkinRepository.save(skin);
    }

    @Override
    public void deleteSkin(Long id) {
        if (!purchasedSkinRepository.existsById(id)) {
            // logger.error("Delete operation failed: Skin with ID {} has not been purchased.", id);
            throw new SkinNotPurchasedException(id);
        }
        // logger.info("Deleting skin with id {}", id);
        purchasedSkinRepository.deleteById(id);
    }
}