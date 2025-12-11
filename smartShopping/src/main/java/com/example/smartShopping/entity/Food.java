package com.example.smartShopping.entity;



import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "foods")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long UnitOfMeasurementId;

    private Long FoodCategoryId;

    private Long userId;

    private String imageUrl;

    private String type; // ingredient, food,...

    @Column(nullable = false, updatable = false)
    private String createdAt;

    private String updatedAt;
}
