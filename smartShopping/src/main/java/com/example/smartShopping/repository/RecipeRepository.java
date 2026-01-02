package com.example.smartShopping.repository;

import com.example.smartShopping.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    Optional<Recipe> findByName(String name);
    
    @Query(value = "SELECT * FROM recipes WHERE jsonb_exists(food_ids, CAST(:foodId AS text))", nativeQuery = true)
    List<Recipe> findByFoodIdContained(@Param("foodId") Long foodId);
}
