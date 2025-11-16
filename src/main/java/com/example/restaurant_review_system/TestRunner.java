package com.example.restaurant_review_system;
import com.example.restaurant_review_system.service.RestaurantService;
import com.example.restaurant_review_system.service.ReviewService;
import com.example.restaurant_review_system.service.VisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TestRunner implements CommandLineRunner {

    private final VisitorService visitorService;
    private final RestaurantService restaurantService;
    private final ReviewService reviewService;

    @Override
    public void run(String... args) {
        System.out.println("Все посетители:");
        visitorService.findAll().forEach(System.out::println);

        System.out.println("\nВсе рестораны:");
        restaurantService.findAll().forEach(System.out::println);

        System.out.println("\nВсе оценки:");
        reviewService.findAll().forEach(System.out::println);
    }
}
