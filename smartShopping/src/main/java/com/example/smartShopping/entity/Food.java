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

    @Column(name = "group_id")
    private Long groupId;

    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private FoodType type;

    private Double price; // Giá mỗi đơn vị

    private Double quantity; // Số lượng tồn kho

    @Column(name = "expiration_date")
    private String expirationDate; // Ngày hết hạn (ISO format)

    @Column(nullable = false, updatable = false)
    private String createdAt;

    private String updatedAt;
}
