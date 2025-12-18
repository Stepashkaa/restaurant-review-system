package com.example.restaurant_review_system.service;

import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.entity.Restaurant;
import com.example.restaurant_review_system.entity.Review;
import com.example.restaurant_review_system.entity.Visitor;
import com.example.restaurant_review_system.mapper.ReviewMapper;
import com.example.restaurant_review_system.repository.ReviewRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock private ReviewRepository reviewRepository;
    @Mock private ReviewMapper reviewMapper;
    @Mock private RestaurantService restaurantService;
    @Mock private VisitorService visitorService;

    @InjectMocks
    private ReviewService reviewService;

    @Test
    void create_setsLinks_saves_recalculatesRating() {
        ReviewRequestDTO dto = new ReviewRequestDTO(1L, 2L, 4, "text");

        Review entity = new Review();
        when(reviewMapper.toEntity(dto)).thenReturn(entity);

        Visitor visitor = new Visitor(); visitor.setId(1L);
        Restaurant restaurant = new Restaurant(); restaurant.setId(2L);

        when(visitorService.findEntityById(1L)).thenReturn(Optional.of(visitor));
        when(restaurantService.findEntityById(2L)).thenReturn(Optional.of(restaurant));

        Review saved = new Review();
        saved.setVisitor(visitor);
        saved.setRestaurant(restaurant);
        when(reviewRepository.save(any(Review.class))).thenReturn(saved);

        // среднее (5+3)/2 = 4.00
        Review r1 = new Review(); r1.setScore(5);
        Review r2 = new Review(); r2.setScore(3);
        when(reviewRepository.findAllByRestaurantId(2L)).thenReturn(List.of(r1, r2));

        when(reviewMapper.toResponseDto(saved))
                .thenReturn(new ReviewResponseDTO(1L, 2L, 4, "text"));

        ReviewResponseDTO result = reviewService.create(dto);

        assertEquals(1L, result.visitorId());
        assertEquals(2L, result.restaurantId());

        verify(restaurantService).updateRating(eq(2L), eq(new BigDecimal("4.00")));
    }
}
