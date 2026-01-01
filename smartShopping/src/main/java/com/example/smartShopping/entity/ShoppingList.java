package com.example.smartShopping.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "shopping_lists")
public class ShoppingList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String note;

    private LocalDate date;

    // admin tạo list
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    // người được giao đi mua
    @ManyToOne
    @JoinColumn(name = "assigned_to_user_id")
    private User assignedToUser;

    // trưởng nhóm
    @Getter(onMethod_=@JsonProperty("belongsToGroupAdmin"))
    private Long belongsToGroupAdminId;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    // ✅ THÊM CÁI NÀY
    @OneToMany(
            mappedBy = "shoppingList",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ShoppingTask> details = new ArrayList<>();

}
