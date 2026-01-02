package com.example.smartShopping.repository;

import com.example.smartShopping.entity.ShoppingTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingTaskRepository
        extends JpaRepository<ShoppingTask, Long> {

    // Query using the FK column directly since there's no relationship mapping
    List<ShoppingTask> findByShoppingListId(Long shoppingListId);

    // Fixed query using the FK column instead of non-existent relationship
    @Query("""
        SELECT t FROM ShoppingTask t
        WHERE t.shoppingListId = :listId
    """)
    List<ShoppingTask> findTasksWithList(@Param("listId") Long listId);
}
