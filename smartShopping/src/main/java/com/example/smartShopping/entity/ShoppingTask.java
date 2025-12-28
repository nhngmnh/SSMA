package com.example.smartShopping.entity;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "shopping_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShoppingTask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String foodName;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private Boolean completed = false;

    // ====== RELATION ======
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shopping_list_id", nullable = false)
    private ShoppingList shoppingList;

    @ManyToOne
    @JoinColumn(name = "food_id")
    private Food food;

}
