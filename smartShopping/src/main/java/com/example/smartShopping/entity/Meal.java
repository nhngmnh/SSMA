package com.example.smartShopping.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "meal_plans")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_plan_id")
    private Long id;

    private String name;

    private String timestamp;

    private String status;

    @Column(name = "food_id")
    private Long foodId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}
