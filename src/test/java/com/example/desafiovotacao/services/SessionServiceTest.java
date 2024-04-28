package com.example.desafiovotacao.services;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.SessionResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.exceptions.SessionNotFoundException;
import com.example.desafiovotacao.exceptions.SessionOpenException;
import com.example.desafiovotacao.repositories.RulingRepository;
import com.example.desafiovotacao.repositories.SessionRepository;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {
    
    @InjectMocks
    private SessionService sessionService;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private RulingRepository rulingRepository;

    @Test
    @DisplayName("Should Create Session")
    void shouldCreateSession() {
        Integer mockedRulingId = 1;
        Integer mockDuration = 60;

        SessionEntity sessionEntity = SessionEntity.builder()
                                                   .duration(mockDuration)
                                                   .rulingId(mockedRulingId)
                                                   .build();

        RulingEntity rulingEntity = RulingEntity.builder()
                                                .id(mockedRulingId)
                                                .title("Ruling Test")
                                                .description("Description test")
                                                .build();

        when(rulingRepository.findById(mockedRulingId)).thenReturn(Optional.of(rulingEntity));
        when(sessionRepository.findAllByRulingId(mockedRulingId)).thenReturn(List.of());
        when(sessionRepository.save(sessionEntity)).thenReturn(sessionEntity);

        SessionResponseDTO sessionResponseDTO = sessionService.create(CreatedSessionDTO.builder()
                                                                                       .duration(mockDuration)
                                                                                       .rulingId(mockedRulingId)
                                                                                       .build());

        Assertions.assertNotNull(sessionResponseDTO);
        Assertions.assertEquals(mockDuration, sessionResponseDTO.getDuration());
        Assertions.assertEquals(mockedRulingId, sessionResponseDTO.getRulingId());
    }

    @Test
    @DisplayName("Should Create Throws FieldValidationException If Missing Fields")
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        Assertions.assertThrows(FieldValidationException.class, () -> sessionService.create(CreatedSessionDTO.builder().build()));
    }

    @Test
    @DisplayName("Should Create Throws RulingNotFoundException If Ruling Not Found")
    void shouldCreateThrowsRulingNotFoundExceptionIfRulingNotFound() {
        Integer mockedRulingId = 1;

        when(rulingRepository.findById(mockedRulingId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RulingNotFoundException.class, () -> sessionService.create(CreatedSessionDTO.builder()
                                                                                                                         .duration(60)
                                                                                                                         .rulingId(mockedRulingId)
                                                                                                                         .build()));
    }

    @Test
    @DisplayName("Should Create Throws RulingNotFoundException If Ruling Not Found")
    void shouldCreateThrowsSessionOpenExceptionIfAlreadyHaveSessionOpen() {
        Integer mockedRulingId = 1;

        RulingEntity rulingEntity = RulingEntity.builder()
                                                .id(mockedRulingId)
                                                .title("Ruling Test")
                                                .description("Description test")
                                                .build();

        when(rulingRepository.findById(mockedRulingId)).thenReturn(Optional.of(rulingEntity));
        when(sessionRepository.findAllByRulingId(mockedRulingId)).thenReturn(List.of(SessionEntity.builder()
                                                                                                  .createdAt(LocalDateTime.now())  
                                                                                                  .duration(60)
                                                                                                  .build()));

        Assertions.assertThrows(SessionOpenException.class, () -> sessionService.create(CreatedSessionDTO.builder()
                                                                                                                         .rulingId(mockedRulingId)
                                                                                                                         .duration(60)
                                                                                                                         .build()));
    }

    @Test
    @DisplayName("Should Find By Id")
    void shouldFindById() {
        Integer mockedId = 1;
        Integer mockedDuration = 60;

        SessionEntity sessionEntity = SessionEntity.builder()
                                                   .id(mockedId)
                                                   .duration(mockedDuration)
                                                   .rulingId(mockedId)
                                                   .build();

        when(sessionRepository.findById(mockedId)).thenReturn(Optional.of(sessionEntity));

        SessionResponseDTO sessionResponseDTO = sessionService.findById(sessionEntity.getId());

        Assertions.assertNotNull(sessionResponseDTO);
        Assertions.assertEquals(mockedId, sessionResponseDTO.getRulingId());
        Assertions.assertEquals(mockedDuration, sessionResponseDTO.getDuration());
    }

    @Test
    @DisplayName("Should Find By Id Throws SessionNotFoundException If Ruling Not Found")
    void shouldFindByIdThrowsSessionNotFoundExceptionIfRulingNotFound() {
        Integer mockedId = 1;

        when(sessionRepository.findById(mockedId)).thenReturn(Optional.empty());

        Assertions.assertThrows(SessionNotFoundException.class, () -> sessionService.findById(mockedId));
    }
}
