package com.example.restaurant_review_system.dto.visitor;

import com.example.restaurant_review_system.entity.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


@Schema(description = "DTO для создания или обновления данных посетителя")
public record VisitorRequestDTO(

        @Schema(
                description = "Имя посетителя",
                example = "Иван Петров",
                nullable = true
        )
        @NotBlank(message = "Имя не может быть пустым")
        @Size(max = 255, message = "Имя должно содержать не более 255 символов")
        String name,

        @Schema(
                description = "Возраст посетителя",
                example = "25"
        )
        @NotNull(message = "Возраст обязателен")
        @Min(value = 1, message = "Возраст должен быть положительным")
        Integer age,

        @Schema(
                description = "Пол посетителя",
                example = "MALE"
        )
        @NotNull(message = "Пол обязателен")
        Gender gender
) {}
