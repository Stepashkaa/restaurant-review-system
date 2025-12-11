package com.example.restaurant_review_system.service;

import com.example.restaurant_review_system.dto.visitor.VisitorRequestDTO;
import com.example.restaurant_review_system.dto.visitor.VisitorResponseDTO;
import com.example.restaurant_review_system.entity.Visitor;
import com.example.restaurant_review_system.mapper.VisitorMapper;
import com.example.restaurant_review_system.repository.VisitorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class VisitorService {

    private final VisitorRepository visitorRepository;
    private final VisitorMapper visitorMapper;

    public List<VisitorResponseDTO> getAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toResponseDto)
                .toList();
    }

    public Optional<VisitorResponseDTO> getById(Long id) {
        return visitorRepository.findById(id)
                .map(visitorMapper::toResponseDto);
    }

    public VisitorResponseDTO create(VisitorRequestDTO dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor.setId(null);
        Visitor saved = visitorRepository.save(visitor);
        return visitorMapper.toResponseDto(saved);
    }

    public Optional<VisitorResponseDTO> update(Long id, VisitorRequestDTO dto) {
        return visitorRepository.findById(id)
                .map(existing -> {
                    visitorMapper.updateEntityFromDto(dto, existing);
                    Visitor saved = visitorRepository.save(existing);
                    return visitorMapper.toResponseDto(saved);
                });
    }

    public boolean delete(Long id) {
        if (!visitorRepository.existsById(id)) {
            return false;
        }
        visitorRepository.deleteById(id);
        return true;
    }
}
