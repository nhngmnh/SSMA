package com.example.smartShopping.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Log {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private String resultCode;
    private String level;
    private String errorMessage;
    private String ip;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
