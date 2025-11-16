package com.example.restaurant_review_system.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Visitor {

    @NotNull
    private Long id;

    @Nullable
    private String name;

    @NotNull
    private Integer age;

    @NotNull
    private Gender gender;
}
