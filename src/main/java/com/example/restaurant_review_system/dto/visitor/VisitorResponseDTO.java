package com.example.restaurant_review_system.dto.visitor;

import com.example.restaurant_review_system.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "DTO с информацией о посетителе, возвращаемой API")
public record VisitorResponseDTO(

        @Schema(
                description = "Уникальный идентификатор посетителя",
                example = "10"
        )
        Long id,

        @Schema(
                description = "Имя посетителя",
                example = "Иван Петров"
        )
        String name,

        @Schema(
                description = "Возраст посетителя",
                example = "25"
        )
        Integer age,

        @Schema(
                description = "Пол посетителя",
                example = "MALE"
        )
        Gender gender
) {}
