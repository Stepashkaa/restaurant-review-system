package com.example.restaurant_review_system.mapper;

import com.example.restaurant_review_system.dto.restaurant.RestaurantRequestDTO;
import com.example.restaurant_review_system.dto.restaurant.RestaurantResponseDTO;
import com.example.restaurant_review_system.entity.Restaurant;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RestaurantMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ratingUser", ignore = true)
    Restaurant toEntity(RestaurantRequestDTO dto);

    RestaurantResponseDTO toResponseDto(Restaurant entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "ratingUser", ignore = true)
    void updateEntityFromDto(RestaurantRequestDTO dto, @MappingTarget Restaurant entity);
}