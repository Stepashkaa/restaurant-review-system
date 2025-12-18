package com.example.restaurant_review_system.service;

import com.example.restaurant_review_system.dto.visitor.VisitorRequestDTO;
import com.example.restaurant_review_system.dto.visitor.VisitorResponseDTO;
import com.example.restaurant_review_system.entity.Gender;
import com.example.restaurant_review_system.entity.Visitor;
import com.example.restaurant_review_system.mapper.VisitorMapper;
import com.example.restaurant_review_system.repository.VisitorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VisitorServiceTest {

    @Mock
    private VisitorRepository visitorRepository;

    @Mock
    private VisitorMapper visitorMapper;

    @InjectMocks
    private VisitorService visitorService;

    @Test
    void getAll_returnsListOfDtos() {
        Visitor v1 = new Visitor();
        v1.setId(1L);
        Visitor v2 = new Visitor();
        v2.setId(2L);

        when(visitorRepository.findAll()).thenReturn(List.of(v1, v2));
        when(visitorMapper.toResponseDto(v1))
                .thenReturn(new VisitorResponseDTO(1L, "Antonina", 20, Gender.FEMALE));
        when(visitorMapper.toResponseDto(v2))
                .thenReturn(new VisitorResponseDTO(2L, null, 30, Gender.MALE));

        List<VisitorResponseDTO> result = visitorService.getAll();

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).id());
        assertEquals(2L, result.get(1).id());

        verify(visitorRepository).findAll();
        verify(visitorMapper).toResponseDto(v1);
        verify(visitorMapper).toResponseDto(v2);
    }

    @Test
    void getById_found_returnsOptionalDto() {
        long id = 10L;
        Visitor v = new Visitor();
        v.setId(id);

        when(visitorRepository.findById(id)).thenReturn(Optional.of(v));
        when(visitorMapper.toResponseDto(v))
                .thenReturn(new VisitorResponseDTO(id, "Luda", 85, Gender.FEMALE));

        Optional<VisitorResponseDTO> result = visitorService.getById(id);

        assertTrue(result.isPresent());
        assertEquals(id, result.get().id());

        verify(visitorRepository).findById(id);
        verify(visitorMapper).toResponseDto(v);
    }

    @Test
    void getById_notFound_returnsEmpty() {
        long id = 404L;
        when(visitorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<VisitorResponseDTO> result = visitorService.getById(id);

        assertTrue(result.isEmpty());
        verify(visitorRepository).findById(id);
        verifyNoInteractions(visitorMapper);
    }

    @Test
    void create_savesEntity_andReturnsDto() {
        VisitorRequestDTO dto = new VisitorRequestDTO("Luda", 85, Gender.FEMALE);

        Visitor entity = new Visitor();
        entity.setName("Luda");
        entity.setAge(85);
        entity.setGender(Gender.FEMALE);

        Visitor saved = new Visitor();
        saved.setId(1L);
        saved.setName("Luda");
        saved.setAge(85);
        saved.setGender(Gender.FEMALE);

        when(visitorMapper.toEntity(dto)).thenReturn(entity);
        when(visitorRepository.save(any(Visitor.class))).thenReturn(saved);
        when(visitorMapper.toResponseDto(saved))
                .thenReturn(new VisitorResponseDTO(1L, "Luda", 85, Gender.FEMALE));

        VisitorResponseDTO result = visitorService.create(dto);

        assertEquals(1L, result.id());
        assertEquals("Luda", result.name());

        // сервис сам выставляет id = null перед сохранением
        assertNull(entity.getId());

        verify(visitorMapper).toEntity(dto);
        verify(visitorRepository).save(entity);
        verify(visitorMapper).toResponseDto(saved);
    }

    @Test
    void update_found_updatesEntity_saves_andReturnsDto() {
        long id = 5L;
        VisitorRequestDTO dto = new VisitorRequestDTO("New", 25, Gender.FEMALE);

        Visitor existing = new Visitor();
        existing.setId(id);

        Visitor saved = new Visitor();
        saved.setId(id);

        when(visitorRepository.findById(id)).thenReturn(Optional.of(existing));
        doNothing().when(visitorMapper).updateEntityFromDto(dto, existing);
        when(visitorRepository.save(existing)).thenReturn(saved);
        when(visitorMapper.toResponseDto(saved))
                .thenReturn(new VisitorResponseDTO(id, "New", 25, Gender.FEMALE));

        Optional<VisitorResponseDTO> result = visitorService.update(id, dto);

        assertTrue(result.isPresent());
        assertEquals("New", result.get().name());

        verify(visitorRepository).findById(id);
        verify(visitorMapper).updateEntityFromDto(dto, existing);
        verify(visitorRepository).save(existing);
        verify(visitorMapper).toResponseDto(saved);
    }

    @Test
    void update_notFound_returnsEmpty() {
        long id = 999L;
        VisitorRequestDTO dto = new VisitorRequestDTO("X", 1, Gender.MALE);
        when(visitorRepository.findById(id)).thenReturn(Optional.empty());

        Optional<VisitorResponseDTO> result = visitorService.update(id, dto);

        assertTrue(result.isEmpty());
        verify(visitorRepository).findById(id);
        verifyNoInteractions(visitorMapper);
        verify(visitorRepository, never()).save(any());
    }

    @Test
    void delete_exists_deletesAndReturnsTrue() {
        long id = 1L;
        when(visitorRepository.existsById(id)).thenReturn(true);

        boolean result = visitorService.delete(id);

        assertTrue(result);
        verify(visitorRepository).existsById(id);
        verify(visitorRepository).deleteById(id);
    }

    @Test
    void delete_notExists_returnsFalse() {
        long id = 2L;
        when(visitorRepository.existsById(id)).thenReturn(false);

        boolean result = visitorService.delete(id);

        assertFalse(result);
        verify(visitorRepository).existsById(id);
        verify(visitorRepository, never()).deleteById(anyLong());
    }
}
