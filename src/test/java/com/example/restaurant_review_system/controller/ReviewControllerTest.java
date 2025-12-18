package com.example.restaurant_review_system.controller;

import com.example.restaurant_review_system.dto.review.ReviewRequestDTO;
import com.example.restaurant_review_system.dto.review.ReviewResponseDTO;
import com.example.restaurant_review_system.service.ReviewService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReviewController.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewService reviewService;

    @Test
    void getAll_returns200() throws Exception {
        when(reviewService.getAll()).thenReturn(List.of(
                new ReviewResponseDTO(1L, 2L, 5, "ok")
        ));

        mockMvc.perform(get("/api/reviews"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].visitorId").value(1))
                .andExpect(jsonPath("$[0].restaurantId").value(2))
                .andExpect(jsonPath("$[0].score").value(5))
                .andExpect(jsonPath("$[0].text").value("ok"));
    }

    @Test
    void getById_found_returns200() throws Exception {
        when(reviewService.getById(1L, 2L))
                .thenReturn(Optional.of(new ReviewResponseDTO(1L, 2L, 5, "ok")));

        mockMvc.perform(get("/api/reviews/1/2"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visitorId").value(1))
                .andExpect(jsonPath("$.restaurantId").value(2))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.text").value("ok"));
    }

    @Test
    void post_create_returns201() throws Exception {
        ReviewRequestDTO dto = new ReviewRequestDTO(1L, 2L, 5, "ok");

        when(reviewService.create(any(ReviewRequestDTO.class)))
                .thenReturn(new ReviewResponseDTO(1L, 2L, 5, "ok"));

        mockMvc.perform(post("/api/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.visitorId").value(1))
                .andExpect(jsonPath("$.restaurantId").value(2))
                .andExpect(jsonPath("$.score").value(5))
                .andExpect(jsonPath("$.text").value("ok"));
    }

    @Test
    void put_update_returns200() throws Exception {
        ReviewRequestDTO dto = new ReviewRequestDTO(1L, 2L, 4, "updated");

        when(reviewService.update(eq(1L), eq(2L), any(ReviewRequestDTO.class)))
                .thenReturn(Optional.of(new ReviewResponseDTO(1L, 2L, 4, "updated")));

        mockMvc.perform(put("/api/reviews/1/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.visitorId").value(1))
                .andExpect(jsonPath("$.restaurantId").value(2))
                .andExpect(jsonPath("$.score").value(4))
                .andExpect(jsonPath("$.text").value("updated"));
    }

    @Test
    void delete_returns204() throws Exception {
        when(reviewService.delete(1L, 2L)).thenReturn(true);

        mockMvc.perform(delete("/api/reviews/1/2"))
                .andExpect(status().isNoContent());
    }
}