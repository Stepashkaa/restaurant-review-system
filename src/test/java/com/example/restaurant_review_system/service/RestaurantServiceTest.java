package com.example.restaurant_review_system.service;


import com.example.restaurant_review_system.dto.restaurant.RestaurantRequestDTO;
import com.example.restaurant_review_system.dto.restaurant.RestaurantResponseDTO;
import com.example.restaurant_review_system.entity.KitchenType;
import com.example.restaurant_review_system.entity.Restaurant;
import com.example.restaurant_review_system.mapper.RestaurantMapper;
import com.example.restaurant_review_system.repository.RestaurantRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class RestaurantServiceTest {

    @Mock
    private RestaurantRepository restaurantRepository;

    @Mock
    private RestaurantMapper restaurantMapper;

    @InjectMocks
    private RestaurantService restaurantService;

    @Test
    void findAll_returnsListOfDtos() {
        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        Restaurant r2 = new Restaurant();
        r2.setId(2L);

        when(restaurantRepository.findAll()).thenReturn(List.of(r1, r2));
        when(restaurantMapper.toResponseDto(r1)).thenReturn(new RestaurantResponseDTO(
                1L, "Restik1", null, KitchenType.ITALIAN,
                new BigDecimal("350"), new BigDecimal("0.00")
        ));

        when(restaurantMapper.toResponseDto(r2)).thenReturn(new RestaurantResponseDTO(
                2L, "Restik2", "desc", KitchenType.RUSSIAN,
                new BigDecimal("1500"), new BigDecimal("4.50")
        ));
        List<RestaurantResponseDTO> result = restaurantService.getAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());
    }

    @Test
    void create_setsRatingZero_saves_andReturnsDto() {
        RestaurantRequestDTO dto = new RestaurantRequestDTO(
                "BaklanPizza", null, KitchenType.ITALIAN, new BigDecimal("400.00"));

        Restaurant entity = new Restaurant();

        Restaurant saved = new Restaurant();
        saved.setId(1L);

        when(restaurantMapper.toEntity(dto)).thenReturn(entity);
        when(restaurantRepository.save(any(Restaurant.class))).thenReturn(saved);
        when(restaurantMapper.toResponseDto(saved)).thenReturn(new RestaurantResponseDTO(
                1L, "BaklanPizza", null, KitchenType.ITALIAN,
                new BigDecimal("400.00"), BigDecimal.ZERO
        ));
        RestaurantResponseDTO result = restaurantService.create(dto);

        assertEquals(1L, result.id());

        // сервис ставит id = null и rating = 0
        assertNull(entity.getId());
        assertEquals(BigDecimal.ZERO, entity.getRatingUser());

    }
}