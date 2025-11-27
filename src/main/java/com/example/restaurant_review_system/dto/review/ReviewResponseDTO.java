package com.example.restaurant_review_system.dto.review;


import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO для отображения данных отзыва в ответах API")
public record ReviewResponseDTO(

        @Schema(
                description = "ID посетителя",
                example = "3"
        )
        Long visitorId,

        @Schema(
                description = "ID ресторана",
                example = "12"
        )
        Long restaurantId,

        @Schema(
                description = "Оценка от 1 до 5",
                example = "4"
        )
        Integer score,

        @Schema(
                description = "Текст отзыва",
                example = "Отличное место, всё понравилось!"
        )
        String text
) {}