package com.example.restaurant_review_system.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Nullable
    @Column(name = "description", length = 1000)
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "kitchen_type", nullable = false, length = 50)
    private KitchenType kitchenType;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "average_check", nullable = false, precision = 10, scale = 2)
    private BigDecimal averageCheck;

    @NotNull
    @DecimalMin("0.0")
    @Column(name = "rating_user", nullable = false, precision = 3, scale = 2)
    private BigDecimal ratingUser;
}
