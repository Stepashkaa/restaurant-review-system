package com.example.restaurant_review_system.controller;

import com.example.restaurant_review_system.dto.restaurant.RestaurantRequestDTO;
import com.example.restaurant_review_system.dto.restaurant.RestaurantResponseDTO;
import com.example.restaurant_review_system.entity.KitchenType;
import com.example.restaurant_review_system.service.RestaurantService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.when;

@WebMvcTest(RestaurantController.class)
class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    RestaurantService restaurantService;

    @Test
    void getAll_returns200() throws Exception {
        when(restaurantService.getAll()).thenReturn(List.of(
                new RestaurantResponseDTO(1L, "restik1", null, KitchenType.ITALIAN,
                        new BigDecimal("650.00"), new BigDecimal("0.00"))
        ));

        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    void post_create_returns201() throws Exception {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "BaklanPizza", null, KitchenType.ITALIAN, new BigDecimal("400.00"));

        when(restaurantService.create(any(RestaurantRequestDTO.class)))
                .thenReturn(new RestaurantResponseDTO(
                        10L, "BaklanPizza", null, KitchenType.ITALIAN,
                        new BigDecimal("400.00"), new BigDecimal("0.00")));

        mockMvc.perform(post("/api/restaurants")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(10));
    }
    @Test
    void put_update_returns200() throws Exception {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "Updated", null, KitchenType.ITALIAN, new BigDecimal("400.00"));

        when(restaurantService.update(eq(1L), any(RestaurantRequestDTO.class)))
                .thenReturn(Optional.of(
                        new RestaurantResponseDTO(
                                1L, "Updated", null,
                                KitchenType.ITALIAN,
                                new BigDecimal("400.00"),
                                new BigDecimal("0.00")
                        )
                ));

        mockMvc.perform(put("/api/restaurants/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    void delete_returns204() throws Exception {
        when(restaurantService.delete(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/restaurants/1"))
                .andExpect(status().isNoContent());
    }

}
