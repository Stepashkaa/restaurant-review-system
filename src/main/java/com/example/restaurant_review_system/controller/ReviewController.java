package com.example.restaurant_review_system.controller;

import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@Tag(name = "Reviews", description = "Операции с отзывами о ресторанах")
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping
    @Operation(summary = "Получить список всех отзывов")
    public List<ReviewResponseDTO> getAll() {
        return reviewService.getAll();
    }

    @GetMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Получить отзыв по ID посетителя и ID ресторана")
    public ResponseEntity<ReviewResponseDTO> getById(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId
    ) {
        return reviewService.getById(visitorId, restaurantId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый отзыв")
    public ResponseEntity<ReviewResponseDTO> create(
            @Valid @RequestBody ReviewRequestDTO dto
    ) {
        ReviewResponseDTO created = reviewService.create(dto);

        URI location = URI.create(
                "/api/reviews/" + created.visitorId() + "/" + created.restaurantId()
        );

        return ResponseEntity.created(location).body(created);
    }

    @PutMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Обновить отзыв по ID посетителя и ID ресторана")
    public ResponseEntity<ReviewResponseDTO> update(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId,
            @Valid @RequestBody ReviewRequestDTO dto
    ) {
        return reviewService.update(visitorId, restaurantId, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{visitorId}/{restaurantId}")
    @Operation(summary = "Удалить отзыв по ID посетителя и ID ресторана")
    public ResponseEntity<Void> delete(
            @Parameter(description = "ID посетителя", example = "1")
            @PathVariable Long visitorId,
            @Parameter(description = "ID ресторана", example = "1")
            @PathVariable Long restaurantId
    ) {
        boolean deleted = reviewService.delete(visitorId, restaurantId);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
