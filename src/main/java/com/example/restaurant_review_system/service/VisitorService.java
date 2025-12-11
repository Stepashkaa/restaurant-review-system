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

    private long generateNewId() {
        return visitorRepository.findAll().stream()
                .mapToLong(Visitor::getId)
                .max()
                .orElse(0L) + 1L;
    }

    public List<VisitorResponseDTO> getAll() {
        return visitorRepository.findAll().stream()
                .map(visitorMapper::toResponseDto)
                .toList();
    }

    public Optional<VisitorResponseDTO> getById(Long id) {
        Visitor visitor = visitorRepository.findById(id);
        return Optional.ofNullable(visitor)
                .map(visitorMapper::toResponseDto);
    }

    public VisitorResponseDTO create(VisitorRequestDTO dto) {
        Visitor visitor = visitorMapper.toEntity(dto);
        visitor.setId(generateNewId());
        visitorRepository.save(visitor);
        return visitorMapper.toResponseDto(visitor);
    }

    public Optional<VisitorResponseDTO> update(Long id, VisitorRequestDTO dto) {
        Visitor existing = visitorRepository.findById(id);

        if (existing == null) {
            return Optional.empty();
        }

        visitorMapper.updateEntityFromDto(dto, existing);

        visitorRepository.remove(existing);
        visitorRepository.save(existing);

        return Optional.of(visitorMapper.toResponseDto(existing));
    }

    public boolean delete(Long id) {
        Visitor existing = visitorRepository.findById(id);

        if (existing == null) {
            return false;
        }

        visitorRepository.remove(existing);
        return true;
    }
}
