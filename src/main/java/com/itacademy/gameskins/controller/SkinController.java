package com.itacademy.gameskins.controller;

import com.itacademy.gameskins.exception.InvalidColorException;
import com.itacademy.gameskins.exception.SkinNotPurchasedException;
import com.itacademy.gameskins.model.Skin;
import com.itacademy.gameskins.service.SkinService;
import com.itacademy.gameskins.exception.SkinAlreadyPurchasedException;
import com.itacademy.gameskins.exception.SkinNotFoundException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/skins")
public class SkinController {

    private final SkinService skinService;
    private static final Logger logger = LoggerFactory.getLogger(SkinController.class);

    @Autowired
    public SkinController(SkinService skinService) {
        this.skinService = skinService;
    }

    @Operation(summary = "Obtener skins disponibles", description = "Devuelve una lista de skins disponibles para comprar.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/available")
    public ResponseEntity<List<Skin>> getAvailableSkins() {
        List<Skin> skins = skinService.getAvailableSkins();
        return ResponseEntity.ok(skins);
    }

    @Operation(summary = "Comprar una skin", description = "Compra una skin mediante su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Skin comprada exitosamente."),
            @ApiResponse(responseCode = "404", description = "Skin no encontrada o ya comprada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PostMapping("/buy")
    public ResponseEntity<Skin> buySkin(
            @Parameter(description = "ID de la skin a comprar", required = true)
            @RequestParam Long id) {
        try {
            Skin purchasedSkin = skinService.buySkin(id);
            return ResponseEntity.ok(purchasedSkin);
        } catch (SkinAlreadyPurchasedException | SkinNotFoundException e) {
            logger.error("Skin not found or already purchased: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Obtener skins compradas", description = "Devuelve una lista de skins compradas por el usuario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/myskins")
    public ResponseEntity<List<Skin>> getMySkins() {
        List<Skin> mySkins = skinService.getMySkins();
        return ResponseEntity.ok(mySkins);
    }

    @Operation(summary = "Actualizar el color de una skin", description = "Actualiza el color de una skin comprada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Color actualizado exitosamente."),
            @ApiResponse(responseCode = "400", description = "Color inválido."),
            @ApiResponse(responseCode = "404", description = "Skin no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @PutMapping("/color")
    public ResponseEntity<Skin> updateSkinColor(
            @Parameter(description = "ID de la skin a actualizar", required = true)
            @RequestParam Long id,
            @Parameter(description = "Nuevo color para la skin", required = true)
            @RequestParam String color) {
        try {
            Skin updatedSkin = skinService.updateSkinColor(id, color);
            return ResponseEntity.ok(updatedSkin);
        } catch (InvalidColorException e) {
            throw e;
            // return ResponseEntity.badRequest().body(null);
        } catch (SkinNotPurchasedException e) {
            throw e;
            // return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Eliminar una skin", description = "Elimina una skin comprada por su ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Skin eliminada exitosamente."),
            @ApiResponse(responseCode = "404", description = "Skin no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteSkin(
            @Parameter(description = "ID de la skin a eliminar", required = true)
            @PathVariable Long id) {
        try {
            skinService.deleteSkin(id);
            return ResponseEntity.noContent().build();
        } catch (SkinNotPurchasedException e) {
            logger.error("Skin not purchased: {}", e.getMessage());
            throw e;
        }
    }

    @Operation(summary = "Obtener una skin por ID", description = "Devuelve los detalles de una skin específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Operación exitosa."),
            @ApiResponse(responseCode = "404", description = "Skin no encontrada."),
            @ApiResponse(responseCode = "500", description = "Error interno del servidor.")
    })
    @GetMapping("/getskin/{id}")
    public ResponseEntity<Skin> getSkinById(
            @Parameter(description = "ID de la skin a buscar", required = true)
            @PathVariable Long id) {
        Skin skin = skinService.getSkinById(id);
        if (skin != null) {
            return ResponseEntity.ok(skin);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}