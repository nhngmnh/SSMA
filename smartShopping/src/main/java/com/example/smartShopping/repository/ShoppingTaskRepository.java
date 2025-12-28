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

    // ✅ Cách 1: Query tự sinh (đủ dùng)
    List<ShoppingTask> findByShoppingListId(Long shoppingListId);

    // ✅ Cách 2: JOIN FETCH (tránh LazyInitializationException)
    @Query("""
        SELECT t FROM ShoppingTask t
        JOIN FETCH t.shoppingList
        WHERE t.shoppingList.id = :listId
    """)
    List<ShoppingTask> findTasksWithList(@Param("listId") Long listId);
}
