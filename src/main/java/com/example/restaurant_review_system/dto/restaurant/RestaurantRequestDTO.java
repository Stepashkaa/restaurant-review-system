package com.example.restaurant_review_system.dto.restaurant;

import com.example.restaurant_review_system.entity.KitchenType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Schema(description = "DTO для создания или обновления данных ресторана")
public record RestaurantRequestDTO(

        @Schema(
                description = "Название ресторана",
                example = "Stepashka"
        )
        @NotBlank(message = "Название ресторана не может быть пустым")
        @Size(max = 255, message = "Название должно содержать не более 255 символов")
        String name,

        @Schema(
                description = "Описание ресторана",
                example = "Уютное место с лучшими суши и роллами"
        )
        @Size(max = 1000, message = "Описание должно содержать не более 1000 символов")
        String description,

        @Schema(
                description = "Тип кухни",
                example = "RUSSIAN"
        )
        @NotNull(message = "Тип кухни обязателен")
        KitchenType kitchenType,

        @Schema(
                description = "Средний чек в ресторане",
                example = "1500.00"
        )
        @NotNull(message = "Средний чек обязателен")
        @DecimalMin(value = "0.0", inclusive = true, message = "Средний чек не может быть отрицательным")
        BigDecimal averageCheck
) {}
