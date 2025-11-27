package com.example.restaurant_review_system.dto.restaurant;

import com.example.restaurant_review_system.entity.KitchenType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

@Schema(description = "DTO для данных ресторана, возвращаемых API")
public record RestaurantResponseDTO(

        @Schema(
                description = "Уникальный идентификатор ресторана",
                example = "10"
        )
        Long id,

        @Schema(
                description = "Название ресторана",
                example = "Tokyo Sushi"
        )
        String name,

        @Schema(
                description = "Описание ресторана",
                example = "Уютное место с лучшими суши и роллами"
        )
        String description,

        @Schema(
                description = "Тип кухни",
                example = "RUSSIAN"
        )
        KitchenType kitchenType,

        @Schema(
                description = "Средний чек",
                example = "1500.00"
        )
        BigDecimal averageCheck,

        @Schema(
                description = "Рейтинг ресторана по отзывам пользователей",
                example = "4.7"
        )
        BigDecimal ratingUser
) {}
