package com.example.restaurant_review_system.service;
import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.entity.Review;
import com.example.restaurant_review_system.mapper.ReviewMapper;
import com.example.restaurant_review_system.repository.RestaurantRepository;
import com.example.restaurant_review_system.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
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
        return reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId)
                .map(reviewMapper::toResponseDto);
    }

    public ReviewResponseDTO create(ReviewRequestDTO dto) {
        Review review = reviewMapper.toEntity(dto);

        Review saved = reviewRepository.save(review);

        recalculateRestaurantRating(saved.getRestaurantId());

        return reviewMapper.toResponseDto(saved);
    }

    public Optional<ReviewResponseDTO> update(Long visitorId, Long restaurantId, ReviewRequestDTO dto) {
        return reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId)
                .map(existing -> {
                    reviewMapper.updateEntityFromDto(dto, existing);
                    Review saved = reviewRepository.save(existing);

                    recalculateRestaurantRating(saved.getRestaurantId());

                    return reviewMapper.toResponseDto(saved);
                });
    }

    public boolean delete(Long visitorId, Long restaurantId) {
        Optional<Review> existingOpt = reviewRepository.findByVisitorIdAndRestaurantId(visitorId, restaurantId);

        if (existingOpt.isEmpty()) {
            return false;
        }

        Review existing = existingOpt.get();
        Long restaurantIdValue = existing.getRestaurantId();

        reviewRepository.delete(existing);
        recalculateRestaurantRating(restaurantIdValue);

        return true;
    }


    private void recalculateRestaurantRating(Long restaurantId) {
        List<Review> reviews = reviewRepository.findAllByRestaurantId(restaurantId);

        if (reviews.isEmpty()) {
            restaurantService.updateRating(restaurantId, BigDecimal.ZERO);
            return;
        }

        int sum = reviews.stream()
                .mapToInt(Review::getScore)
                .sum();

        BigDecimal newRating = BigDecimal.valueOf(sum)
                .divide(BigDecimal.valueOf(reviews.size()), 2, RoundingMode.HALF_UP);

        restaurantService.updateRating(restaurantId, newRating);
    }

    public Page<ReviewResponseDTO> getPageSortedByScoreDesc(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "score"));

        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        List<ReviewResponseDTO> content = reviewPage.getContent().stream()
                .map(reviewMapper::toResponseDto)
                .toList();

        return new PageImpl<>(content, pageable, reviewPage.getTotalElements());
    }

    public Page<ReviewResponseDTO> getPageSortedByScore(int page, int size, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, "score"));

        Page<Review> reviewPage = reviewRepository.findAll(pageable);

        List<ReviewResponseDTO> content = reviewPage.getContent().stream()
                .map(reviewMapper::toResponseDto)
                .toList();

        return new PageImpl<>(content, pageable, reviewPage.getTotalElements());
    }
}
