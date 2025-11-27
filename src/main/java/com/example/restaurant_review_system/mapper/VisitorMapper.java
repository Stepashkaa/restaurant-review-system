package com.example.restaurant_review_system.mapper;

import com.example.restaurant_review_system.dto.visitor.VisitorRequestDTO;
import com.example.restaurant_review_system.dto.visitor.VisitorResponseDTO;
import com.example.restaurant_review_system.entity.Visitor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface VisitorMapper {

    @Mapping(target = "id", ignore = true)
    Visitor toEntity(VisitorRequestDTO dto);

    VisitorResponseDTO toResponseDto(Visitor visitor);

    @Mapping(target = "id", ignore = true)
    void updateEntityFromDto(VisitorRequestDTO dto, @MappingTarget Visitor visitor);
}
