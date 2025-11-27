package com.example.restaurant_review_system.service;

import com.example.restaurant_review_system.dto.restaurant.RestaurantRequestDTO;
import com.example.restaurant_review_system.dto.restaurant.RestaurantResponseDTO;
import com.example.restaurant_review_system.entity.Restaurant;
import com.example.restaurant_review_system.mapper.RestaurantMapper;
import com.example.restaurant_review_system.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final RestaurantMapper restaurantMapper;

    private long generateNewId() {
        return restaurantRepository.findAll().stream()
                .mapToLong(Restaurant::getId)
                .max()
                .orElse(0L) + 1L;
    }

    public List<RestaurantResponseDTO> getAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

    public Optional<RestaurantResponseDTO> getById(Long id) {
        Restaurant restaurant = restaurantRepository.findById(id);
        return Optional.ofNullable(restaurant)
                .map(restaurantMapper::toResponseDto);
    }

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);

        restaurant.setId(generateNewId());
        restaurant.setRatingUser(BigDecimal.ZERO);

        restaurantRepository.save(restaurant);
        return restaurantMapper.toResponseDto(restaurant);
    }

    public Optional<RestaurantResponseDTO> update(Long id, RestaurantRequestDTO dto) {
        Restaurant existing = restaurantRepository.findById(id);

        if (existing == null) {
            return Optional.empty();
        }

        restaurantMapper.updateEntityFromDto(dto, existing);

        restaurantRepository.remove(existing);
        restaurantRepository.save(existing);

        return Optional.of(restaurantMapper.toResponseDto(existing));
    }

    public boolean delete(Long id) {
        Restaurant existing = restaurantRepository.findById(id);

        if (existing == null) {
            return false;
        }

        restaurantRepository.remove(existing);
        return true;
    }

    public void updateRating(Long restaurantId, BigDecimal newRating) {
        Restaurant existing = restaurantRepository.findById(restaurantId);

        if (existing != null) {
            existing.setRatingUser(newRating);

                restaurantRepository.remove(existing);
                restaurantRepository.save(existing);
        }
    }
}
