package com.example.restaurant_review_system.mapper;

import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ReviewMapper {

    Review toEntity(ReviewRequestDTO dto);

    ReviewResponseDTO toResponseDto(Review entity);

    void updateEntityFromDto(ReviewRequestDTO dto, @MappingTarget Review entity);
}
