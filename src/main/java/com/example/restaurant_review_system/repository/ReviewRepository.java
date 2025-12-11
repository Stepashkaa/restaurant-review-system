package com.example.restaurant_review_system.repository;

import com.example.restaurant_review_system.entity.Review;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Review.ReviewPk> {

    Optional<Review> findByVisitorIdAndRestaurantId(Long visitorId, Long restaurantId);

    List<Review> findAllByRestaurantId(Long restaurantId);

    Page<Review> findAll(Pageable pageable);
}
