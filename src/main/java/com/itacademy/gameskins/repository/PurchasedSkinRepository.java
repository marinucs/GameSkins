package com.itacademy.gameskins.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.itacademy.gameskins.model.Skin;

public interface PurchasedSkinRepository extends JpaRepository<Skin, Long> { }