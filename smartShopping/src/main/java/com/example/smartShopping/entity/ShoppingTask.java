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

    // Chỉ lưu ID, không có ràng buộc khóa ngoại
    @Column(name = "shopping_list_id", nullable = false)
    private Long shoppingListId;

    @Column(name = "food_id")
    private Long foodId;

}
