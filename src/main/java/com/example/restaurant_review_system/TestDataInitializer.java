package com.example.restaurant_review_system;

import com.example.restaurant_review_system.entity.*;
import com.example.restaurant_review_system.service.RestaurantService;
import com.example.restaurant_review_system.service.ReviewService;
import com.example.restaurant_review_system.service.VisitorService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
@RequiredArgsConstructor
public class TestDataInitializer {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @PostConstruct
    public void initData(){
        Visitor v1 = new Visitor(1L, "Иван", 25, Gender.MALE);
        Visitor v2 = new Visitor(2L, null, 30, Gender.FEMALE);
        Visitor v3 = new Visitor(3L, "Салих", 55, Gender.MALE);

        visitorService.save(v1);
        visitorService.save(v2);
        visitorService.save(v3);

        Restaurant r1 = Restaurant.builder()
                .id(1L)
                .name("StepikFood")
                .description("вкусная русская кухня")
                .kitchenType(KitchenType.RUSSIAN)
                .averageCheck(new BigDecimal("15.00"))
                .ratingUser(BigDecimal.ZERO)
                .build();

        Restaurant r2 = Restaurant.builder()
                .id(1L)
                .name("IvankoFood")
                .description("вкусная татрская кухня")
                .kitchenType(KitchenType.RUSSIAN)
                .averageCheck(new BigDecimal("55.00"))
                .ratingUser(BigDecimal.ZERO)
                .build();

        restaurantService.save(r1);
        restaurantService.save(r2);

        Review review1 = new Review(1L, 1L, 5, "Нормасик ребятки готовят");
        Review review2 = new Review(2L, 1L, 4, null);
        Review review3 = new Review(3L, 2L, 3, "ФИГНЯЯЯЯЯЯ");

        reviewService.save(review1);
        reviewService.save(review2);
        reviewService.save(review3);
    }
}
