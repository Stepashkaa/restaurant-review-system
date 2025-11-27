package com.example.restaurant_review_system.repository;

import com.example.restaurant_review_system.entity.Review;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Repository
public class ReviewRepository {

    private final List<Review> reviews = new ArrayList<>();

    public void save(Review review) {
        reviews.removeIf(r ->
                r.getVisitorId().equals(review.getVisitorId())
                    && r.getRestaurantId().equals(review.getRestaurantId()));

        reviews.add(review);
    }

    public void remove(Review review){
        reviews.removeIf(r -> r.getVisitorId().equals(review.getVisitorId()) && r.getRestaurantId().equals(review.getRestaurantId()));
    }

    public List<Review> findAll(){
        return Collections.unmodifiableList(reviews);
    }

    public Review findById(Long visitorId, Long restaurantId){
        return reviews.stream()
                .filter(r -> r.getVisitorId().equals(visitorId) && r.getRestaurantId().equals(restaurantId))
                .findFirst()
                .orElse(null);
    }
}
