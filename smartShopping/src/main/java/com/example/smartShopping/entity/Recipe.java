package com.example.smartShopping.entity;

import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import org.hibernate.annotations.Type;
import jakarta.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recipes")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recipe_id")
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "html_content", columnDefinition = "TEXT")
    private String htmlContent;

    // Danh sách food IDs (nguyên liệu cần cho món ăn)
    @Type(JsonBinaryType.class)
    @Column(name = "food_ids", columnDefinition = "jsonb")
    @Builder.Default
    private List<Long> foodIds = new ArrayList<>();

    @Column(name = "group_id")
    private Long groupId;

    @Column(name = "is_public")
    private Boolean isPublic = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private String createdAt;

    @Column(name = "updated_at")
    private String updatedAt;
}
