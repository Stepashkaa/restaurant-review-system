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

    public List<RestaurantResponseDTO> getAll() {
        return restaurantRepository.findAll().stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

    public Optional<RestaurantResponseDTO> getById(Long id) {
        return restaurantRepository.findById(id)
                .map(restaurantMapper::toResponseDto);
    }

    public RestaurantResponseDTO create(RestaurantRequestDTO dto) {
        Restaurant restaurant = restaurantMapper.toEntity(dto);

        restaurant.setId(null);
        restaurant.setRatingUser(BigDecimal.ZERO);

        Restaurant saved = restaurantRepository.save(restaurant);
        return restaurantMapper.toResponseDto(saved);
    }

    public Optional<RestaurantResponseDTO> update(Long id, RestaurantRequestDTO dto) {
        return restaurantRepository.findById(id)
                .map(existing -> {
                    restaurantMapper.updateEntityFromDto(dto, existing);
                    Restaurant saved = restaurantRepository.save(existing);
                    return restaurantMapper.toResponseDto(saved);
                });
    }

    public boolean delete(Long id) {
        if (!restaurantRepository.existsById(id)) {
            return false;
        }
        restaurantRepository.deleteById(id);
        return true;
    }

    public void updateRating(Long restaurantId, BigDecimal newRating) {
        restaurantRepository.findById(restaurantId)
                .ifPresent(r -> {
                    r.setRatingUser(newRating);
                    restaurantRepository.save(r);
                });
    }

    public List<RestaurantResponseDTO> findByMinRating(BigDecimal minRating) {
        return restaurantRepository.findByRatingUserGreaterThanEqual(minRating).stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }

    public List<RestaurantResponseDTO> findByMinRatingUsingQuery(BigDecimal minRating) {
        return restaurantRepository.findWithMinRating(minRating).stream()
                .map(restaurantMapper::toResponseDto)
                .toList();
    }
}
