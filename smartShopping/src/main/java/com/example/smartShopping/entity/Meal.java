package com.example.smartShopping.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

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

    // Danh sách recipe IDs (nhiều món trong 1 bữa ăn)
    @Type(JsonBinaryType.class)
    @Column(name = "recipe_ids", columnDefinition = "jsonb")
    @Builder.Default
    private List<Long> recipeIds = new ArrayList<>();

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}
