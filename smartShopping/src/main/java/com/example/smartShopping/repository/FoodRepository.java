package com.example.smartShopping.repository;

import com.example.smartShopping.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {

    Optional<Food> findByNameAndUserId(String name, Long userId);
    Optional<Food> findByNameIgnoreCase(String name);

    boolean existsByNameIgnoreCase(String name);
    List<Food> findByNameIgnoreCaseAndUserId(String name, Long userId);
    List<Food> findByNameContainingIgnoreCase(String keyword);
    List<Food> findByGroupId(Long groupId);

}
