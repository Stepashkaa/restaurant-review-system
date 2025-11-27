package com.example.restaurant_review_system.controller;

import com.example.restaurant_review_system.dto.visitor.VisitorRequestDTO;
import com.example.restaurant_review_system.dto.visitor.VisitorResponseDTO;
import com.example.restaurant_review_system.service.VisitorService;
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
@RequestMapping("/api/visitors")
@RequiredArgsConstructor
@Tag(name = "Visitors", description = "Операции с посетителями ресторанов")
public class VisitorController {

    private final VisitorService visitorService;

    @GetMapping
    @Operation(summary = "Получить всех посетителей")
    public List<VisitorResponseDTO> getAll() {
        return visitorService.getAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить посетителя по ID")
    public ResponseEntity<VisitorResponseDTO> getById(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id) {
        return visitorService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Создать нового посетителя")
    public ResponseEntity<VisitorResponseDTO> create(@Valid @RequestBody VisitorRequestDTO dto) {
        VisitorResponseDTO created = visitorService.create(dto);

        return ResponseEntity
                .created(URI.create("/api/visitors/" + created.id()))
                .body(created);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные посетителя по ID")
    public ResponseEntity<VisitorResponseDTO> update(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id,
            @Valid @RequestBody VisitorRequestDTO dto
    ) {
        return visitorService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить посетителя по ID")
    public ResponseEntity<Void> delete(
            @Parameter(description = "Идентификатор посетителя", example = "1")
            @PathVariable Long id) {
        boolean deleted = visitorService.delete(id);

        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
