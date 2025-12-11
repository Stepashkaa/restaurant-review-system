package com.example.restaurant_review_system.entity;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reviews")
@IdClass(Review.ReviewPk.class)
public class Review {

    @Id
    @Column(name = "visitor_id", nullable = false)
    @NotNull
    private Long visitorId;

    @Id
    @Column(name = "restaurant_id", nullable = false)
    @NotNull
    private Long restaurantId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "visitor_id", insertable = false, updatable = false)
    private Visitor visitor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "restaurant_id", insertable = false, updatable = false)
    private Restaurant restaurant;

    @NotNull
    @Min(1)
    @Max(5)
    @Column(name = "score", nullable = false)
    private int score;

    @Nullable
    @Column(name = "text", length = 2000)
    private String text;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ReviewPk implements Serializable {
        private Long visitorId;
        private Long restaurantId;
    }
}
