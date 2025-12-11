package com.example.restaurant_review_system.repository;
import com.example.restaurant_review_system.entity.Restaurant;
import com.example.restaurant_review_system.entity.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    List<Restaurant> findByRatingUserGreaterThanEqual(BigDecimal rating);

    @Query("SELECT r FROM Restaurant r WHERE r.ratingUser >= :minRating")
    List<Restaurant> findWithMinRating(@Param("minRating") BigDecimal minRating);
}