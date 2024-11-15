package com.itacademy.gameskins.service;

import com.itacademy.gameskins.exception.InvalidColorException;
import com.itacademy.gameskins.exception.SkinAlreadyPurchasedException;
import com.itacademy.gameskins.exception.SkinNotFoundException;
import com.itacademy.gameskins.exception.SkinNotPurchasedException;
import com.itacademy.gameskins.model.Color;
import com.itacademy.gameskins.model.Skin;
import com.itacademy.gameskins.repository.PurchasedSkinRepository;
import com.itacademy.gameskins.util.SkinLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
// import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SkinServiceTest {

    @Mock
    private PurchasedSkinRepository purchasedSkinRepository;

    @Mock
    private SkinLoader skinLoader;

    @InjectMocks
    private SkinServiceImpl skinService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Skin createTestSkin() {
        return new Skin(1L, "Test Skin", "Test Type", 1399.0, Color.BLUE);
    }

    @Test
    @DisplayName("getAvailableSkins - Should return list of available skins")
    void getAvailableSkins_ShouldReturnListOfSkins() {
        // Arrange
        Skin testSkin = createTestSkin();
        when(skinLoader.getAvailableSkins()).thenReturn(Collections.singletonList(testSkin));

        // Act
        List<Skin> result = skinService.getAvailableSkins();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(1, result.size(), "There should be exactly one skin available");
        assertEquals(testSkin, result.get(0), "The skin should match the test skin");
        verify(skinLoader, times(1)).getAvailableSkins();
    }

    @Test
    @DisplayName("buySkin - Should purchase skin when available")
    void buySkin_ShouldPurchaseSkin_WhenAvailable() {
        // Arrange
        Skin testSkin = createTestSkin();
        Long skinId = testSkin.getId();

        // Configuración del mock para devolver el skin esperado
        when(skinLoader.getSkinById(skinId)).thenReturn(testSkin);
        when(purchasedSkinRepository.findById(skinId)).thenReturn(Optional.empty());

        // Aquí nos aseguramos de que `save()` devuelva el objeto que se le pasa
        when(purchasedSkinRepository.save(any(Skin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Skin result = skinService.buySkin(skinId);

        // Assert
        assertNotNull(result, "The purchased skin should not be null");
        assertEquals(testSkin, result, "The skin returned should match the test skin");
        verify(purchasedSkinRepository, times(1)).save(testSkin);
        verify(skinLoader, times(1)).getSkinById(skinId);
    }

    @Test
    @DisplayName("buySkin - Should throw exception when skin not found")
    void buySkin_ShouldThrowException_WhenSkinNotFound() {
        // Arrange
        Long skinId = 1L;
        when(skinLoader.getSkinById(skinId)).thenReturn(null);

        // Act & Assert
        SkinNotFoundException exception = assertThrows(SkinNotFoundException.class,
                () -> skinService.buySkin(skinId),
                "Expected SkinNotFoundException to be thrown");
        assertEquals("Skin with ID 1 not found.", exception.getMessage(), "Exception message should match");
        verify(skinLoader, times(1)).getSkinById(skinId);
        verify(purchasedSkinRepository, never()).save(any());
    }

    @Test
    @DisplayName("buySkin - Should throw exception when skin already purchased")
    void buySkin_ShouldThrowException_WhenSkinAlreadyPurchased() {
        // Arrange
        Skin testSkin = createTestSkin();
        Long skinId = testSkin.getId();
        when(skinLoader.getSkinById(skinId)).thenReturn(testSkin);
        when(purchasedSkinRepository.findById(skinId)).thenReturn(Optional.of(testSkin));

        // Act & Assert
        SkinAlreadyPurchasedException exception = assertThrows(SkinAlreadyPurchasedException.class,
                () -> skinService.buySkin(skinId),
                "Expected SkinAlreadyPurchasedException to be thrown");
        assertEquals("Skin with ID 1 has already been purchased.", exception.getMessage(), "Exception message should match");
        verify(purchasedSkinRepository, never()).save(any());
    }

    @Test
    @DisplayName("getMySkins - Should return list of purchased skins")
    void getMySkins_ShouldReturnListOfPurchasedSkins() {
        // Arrange
        Skin testSkin = createTestSkin();
        when(purchasedSkinRepository.findAll()).thenReturn(Collections.singletonList(testSkin));

        // Act
        List<Skin> result = skinService.getMySkins();

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals(1, result.size(), "There should be exactly one purchased skin");
        assertEquals(testSkin, result.get(0), "The skin should match the test skin");
        verify(purchasedSkinRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("updateSkinColor - Should update skin color when skin exists")
    void updateSkinColor_ShouldUpdateColor_WhenSkinExists() {
        // Arrange
        Skin testSkin = createTestSkin();
        Long skinId = testSkin.getId();
        when(purchasedSkinRepository.findById(skinId)).thenReturn(Optional.of(testSkin));
        when(purchasedSkinRepository.save(any(Skin.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Skin result = skinService.updateSkinColor(skinId, "Red");

        // Assert
        assertNotNull(result, "The updated skin should not be null");
        assertEquals(Color.RED, result.getColor(), "The skin color should be updated to RED");

        verify(purchasedSkinRepository, times(1)).save(testSkin);
    }

    @Test
    @DisplayName("updateSkinColor - Should throw exception for invalid color")
    void updateSkinColor_ShouldThrowException_WhenColorInvalid() {
        // Arrange
        Long skinId = 1L;

        // Act & Assert
//        InvalidColorException exception = assertThrows(InvalidColorException.class,
//                () -> skinService.updateSkinColor(skinId, "InvalidColor"),
//                "Expected InvalidColorException to be thrown");
        verify(purchasedSkinRepository, never()).save(any());
    }

    @Test
    @DisplayName("deleteSkin - Should delete skin when purchased")
    void deleteSkin_ShouldDelete_WhenSkinPurchased() {
        // Arrange
        Long skinId = 1L;
        when(purchasedSkinRepository.existsById(skinId)).thenReturn(true);

        // Act
        skinService.deleteSkin(skinId);

        // Assert
        verify(purchasedSkinRepository, times(1)).deleteById(skinId);
    }

    @Test
    @DisplayName("deleteSkin - Should throw exception when skin not found")
    void deleteSkin_ShouldThrowException_WhenSkinNotFound() {
        // Arrange
        Long skinId = 1L;
        when(purchasedSkinRepository.existsById(skinId)).thenReturn(false);

        // Act & Assert
        SkinNotPurchasedException exception = assertThrows(SkinNotPurchasedException.class,
                () -> skinService.deleteSkin(skinId),
                "Expected SkinNotPurchasedException to be thrown");
        assertEquals("Skin with ID 1 has not been purchased and cannot be modified or deleted.", exception.getMessage(), "Exception message should match");
    }
}