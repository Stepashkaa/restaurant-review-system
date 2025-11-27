package com.example.restaurant_review_system.dto.review;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO для создания или обновления отзыва о ресторане")
public record ReviewRequestDTO(

        @Schema(
                description = "ID посетителя, оставляющего отзыв",
                example = "3"
        )
        @NotNull(message = "ID посетителя обязателен")
        Long visitorId,

        @Schema(
                description = "ID ресторана, для которого оставляется отзыв",
                example = "12"
        )
        @NotNull(message = "ID ресторана обязателен")
        Long restaurantId,

        @Schema(
                description = "Оценка ресторана",
                example = "5",
                minimum = "1",
                maximum = "5"
        )
        @NotNull(message = "Оценка обязателена")
        @Min(value = 1, message = "Оценка не может быть ниже 1")
        @Max(value = 5, message = "Оценка не может быть выше 5")
        Integer score,

        @Schema(
                description = "Текст отзыва",
                example = "Все очень вкусно!",
                nullable = true
        )
        @Size(max = 1000, message = "Текст отзыва должен быть не длиннее 1000 символов")
        String text
) {}
