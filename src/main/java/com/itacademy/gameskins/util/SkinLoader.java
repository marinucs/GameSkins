package com.itacademy.gameskins.util;

import com.itacademy.gameskins.model.Skin;
import com.itacademy.gameskins.model.Color;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.List;

@Component
public class SkinLoader {
    private static final Logger logger = LoggerFactory.getLogger(SkinLoader.class);
    private List<Skin> availableSkins;

    @PostConstruct
    public void loadSkins() {
        try {
            ObjectMapper mapper = new ObjectMapper();

            // Register a custom deserializer for the Color enum
            SimpleModule module = new SimpleModule();
            module.addDeserializer(Color.class, new com.fasterxml.jackson.databind.JsonDeserializer<>() {
                @Override
                public Color deserialize(com.fasterxml.jackson.core.JsonParser p,
                                         com.fasterxml.jackson.databind.DeserializationContext ctxt)
                        throws java.io.IOException {
                    String colorValue = p.getText();
                    return Color.fromString(colorValue);
                }
            });
            mapper.registerModule(module);

            // Load and parse the JSON file
            TypeReference<List<Skin>> typeReference = new TypeReference<>() {};
            InputStream inputStream = TypeReference.class.getResourceAsStream("/skins.json");
            availableSkins = mapper.readValue(inputStream, typeReference);
            logger.info("Available skins loaded successfully.");
        } catch (Exception e) {
            logger.error("Failed to load skins: {}", e.getMessage());
        }
    }

    public List<Skin> getAvailableSkins() {
        return availableSkins;
    }

    public Skin getSkinById(Long id) {
        return availableSkins.stream()
                .filter(skin -> skin.getId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
