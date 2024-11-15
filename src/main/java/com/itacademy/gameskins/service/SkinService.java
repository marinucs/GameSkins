package com.itacademy.gameskins.service;
import com.itacademy.gameskins.model.Color;
import com.itacademy.gameskins.model.Skin;

import java.util.List;

public interface SkinService {
    List<Skin> getAvailableSkins();
    List<Skin> getMySkins();
    Skin buySkin(Long id);
    Skin getSkinById(Long id);
    Skin updateSkinColor(Long id, String color);
    void deleteSkin(Long id);
}
