package com.example.restaurant_review_system.entity;

import jakarta.annotation.Nullable;
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
public class Restaurant {
    @NotNull
    private Long id;

    @NotNull
    private String name;

    @Nullable
    private String description;

    @NotNull
    private KitchenType kitchenType;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal averageCheck;

    @NotNull
    private BigDecimal ratingUser;
}
