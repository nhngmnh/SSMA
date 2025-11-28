package com.example.smartShopping.repository;


import com.example.smartShopping.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodRepository extends JpaRepository<Food, Long> {
}
