package com.itacademy.gameskins.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "purchased_skins")
public class Skin {
//    @Id private Long id;
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    private String name;
    private String type;
    private Double price;
    @Enumerated(EnumType.STRING) // Guarda el nombre del enum como texto
    private Color color;
}