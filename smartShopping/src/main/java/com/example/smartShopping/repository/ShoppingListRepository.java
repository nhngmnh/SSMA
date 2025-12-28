package com.example.smartShopping.repository;

import com.example.smartShopping.entity.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShoppingListRepository extends JpaRepository<ShoppingList, Long> {

    @Query("""
        SELECT DISTINCT l FROM ShoppingList l
        LEFT JOIN FETCH l.details
    """)
    List<ShoppingList> findAllWithTasks();
}
