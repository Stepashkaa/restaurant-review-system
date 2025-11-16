package com.example.restaurant_review_system.service;
import com.example.restaurant_review_system.entity.Review;
import com.example.restaurant_review_system.repository.RestaurantRepository;
import com.example.restaurant_review_system.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final RestaurantRepository restaurantRepository;

    public void save(Review review) {
        reviewRepository.save(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public void remove(Review review) {
        reviewRepository.remove(review);
        recalculateRestaurantRating(review.getRestaurantId());
    }

    public List<Review> findAll() {
        return reviewRepository.findAll();
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

        restaurantRepository.findAll().stream()
                .filter(r -> restaurantId.equals(r.getId()))
                .findFirst()
                .ifPresent(restaurant -> restaurant.setRatingUser(newRating));
    }

}
