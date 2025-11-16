package com.example.restaurant_review_system;

import com.example.restaurant_review_system.service.RestaurantService;
import com.example.restaurant_review_system.service.VisitorService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestaurantReviewSystemApplication {

	public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(RestaurantReviewSystemApplication.class, args);

        VisitorService visitorService = context.getBean(VisitorService.class);
        RestaurantService restaurantService = context.getBean(RestaurantService.class);

        System.out.println("Количество посетителей: " + visitorService.findAll().size());
        System.out.println("Количество ресторанов: " + restaurantService.findAll().size());
	}

}
