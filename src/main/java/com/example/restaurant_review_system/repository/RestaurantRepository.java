package com.example.restaurant_review_system.repository;
import com.example.restaurant_review_system.entity.Restaurant;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Repository
public class RestaurantRepository {

    private final List<Restaurant> restaurants = new ArrayList<>();

    public void save(Restaurant restaurant) {
        restaurants.add(restaurant);
    }

    public void remove(Restaurant restaurant) {
        restaurants.removeIf(r -> r.getId().equals(restaurant.getId()));
    }

    public List<Restaurant> findAll() {
        return Collections.unmodifiableList(restaurants);
    }
}