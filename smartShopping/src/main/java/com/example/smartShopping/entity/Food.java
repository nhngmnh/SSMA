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

    @Column(name = "unit_of_measurement_id")
    private Long unitOfMeasurementId;

    @Column(name = "food_category_id")
    private Long foodCategoryId;

    @Column(name = "user_id")
    private Long userId;

    private String imageUrl;

    private String type;

    @Column(nullable = false, updatable = false)
    private String createdAt;

    private String updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "unit_of_measurement_id", insertable = false, updatable = false)
    private Unit unitOfMeasurement;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_category_id", insertable = false, updatable = false)
    private Category foodCategory;
}
