package com.example.smartShopping.repository;

import com.example.smartShopping.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByNameAndUserId(String name, Long userId);
}
