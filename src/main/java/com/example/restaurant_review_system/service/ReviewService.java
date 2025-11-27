package com.example.restaurant_review_system.service;
import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.entity.Review;
import com.example.restaurant_review_system.mapper.ReviewMapper;
import com.example.restaurant_review_system.repository.RestaurantRepository;
import com.example.restaurant_review_system.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final ReviewMapper reviewMapper;
    private final RestaurantService restaurantService;

    public List<ReviewResponseDTO> getAll() {
        return reviewRepository.findAll().stream()
                .map(reviewMapper::toResponseDto)
                .toList();
    }

    public Optional<ReviewResponseDTO> getById(Long visitorId, Long restaurantId) {
        Review review = reviewRepository.findById(visitorId, restaurantId);
        return Optional.ofNullable(review)
                .map(reviewMapper::toResponseDto);
    }

    public ReviewResponseDTO create(ReviewRequestDTO dto) {
        Review review = reviewMapper.toEntity(dto);
        reviewRepository.save(review);

        recalculateRestaurantRating(dto.restaurantId());

        return reviewMapper.toResponseDto(review);
    }

    public Optional<ReviewResponseDTO> update(Long visitorId, Long restaurantId, ReviewRequestDTO dto) {
        Review existing = reviewRepository.findById(visitorId, restaurantId);

        if (existing == null) {
            return Optional.empty();
        }

        reviewMapper.updateEntityFromDto(dto, existing);
        reviewRepository.save(existing);

        recalculateRestaurantRating(existing.getRestaurantId());

        return Optional.of(reviewMapper.toResponseDto(existing));
    }

    public boolean delete(Long visitorId, Long restaurantId) {
        Review existing = reviewRepository.findById(visitorId, restaurantId);

        if (existing == null) {
            return false;
        }

        reviewRepository.remove(existing);
        recalculateRestaurantRating(existing.getRestaurantId());

        return true;
    }


    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> allReviews = reviewRepository.findAll();

        int sum = 0;
        int count = 0;
        for (Review review : allReviews) {
            if (restaurantId.equals(review.getRestaurantId())) {
                sum += review.getScore();
                count++;
            }
        }

        BigDecimal newRating;
        if (count == 0) {
            newRating = BigDecimal.ZERO;
        } else {
            newRating = BigDecimal.valueOf(sum)
                    .divide(BigDecimal.valueOf(count), 2, RoundingMode.HALF_UP);
        }

        restaurantService.updateRating(restaurantId, newRating);
    }

}
