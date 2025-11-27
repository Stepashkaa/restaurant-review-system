package com.example.restaurant_review_system.controller;

import com.example.restaurant_review_system.dto.restaurant.RestaurantRequestDTO;
import com.example.restaurant_review_system.dto.restaurant.RestaurantResponseDTO;
import com.example.restaurant_review_system.service.RestaurantService;
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
@RequestMapping("/api/restaurants")
@RequiredArgsConstructor
@Tag(name = "Restaurants", description = "Операции с ресторанами")
public class RestaurantController {

    private final RestaurantService restaurantService;

    @GetMapping
    @Operation(summary = "Получить список всех ресторанов")
    public List<RestaurantResponseDTO> getAll() {
        return restaurantService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить ресторан по ID")
    public ResponseEntity<RestaurantResponseDTO> getById(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id) {
        return restaurantService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать новый ресторан")
    public ResponseEntity<RestaurantResponseDTO> create(
            @Valid @RequestBody RestaurantRequestDTO dto
    ) {
        RestaurantResponseDTO created = restaurantService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/restaurants/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить ресторан по ID")
    public ResponseEntity<RestaurantResponseDTO> update(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody RestaurantRequestDTO dto
    ) {
        return restaurantService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить ресторан по ID")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор ресторана", example = "1")
            @PathVariable Long id) {
        boolean deleted = restaurantService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
