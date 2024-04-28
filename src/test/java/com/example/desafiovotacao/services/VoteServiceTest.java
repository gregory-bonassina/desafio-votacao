package com.example.desafiovotacao.services;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;
import com.example.desafiovotacao.dtos.responses.VoteResponseDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.entities.VoteEntity;
import com.example.desafiovotacao.exceptions.AssociateNotFoundException;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.SessionClosedException;
import com.example.desafiovotacao.exceptions.SessionNotFoundException;
import com.example.desafiovotacao.repositories.AssociateRepository;
import com.example.desafiovotacao.repositories.SessionRepository;
import com.example.desafiovotacao.repositories.VoteRepository;

@ExtendWith(MockitoExtension.class)
public class VoteServiceTest {
    
    @InjectMocks
    private VoteService voteService;

    @Mock
    private VoteRepository voteRepository;
    @Mock
    private AssociateRepository associateRepository;
    @Mock
    private SessionRepository sessionRepository;

    @Test
    @DisplayName("Should Create Vote")
    void shouldCreateVote() {
        Integer mockedId = 1;

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(mockedId)
                                                         .name("Associate Test")
                                                         .cpf("661.338.900-53")
                                                         .build();

        VoteEntity voteEntity = VoteEntity.builder()
                                          .associateId(mockedId)
                                          .sessionId(mockedId)
                                          .resultVote(true)
                                          .build();

        SessionEntity sessionEntity = SessionEntity.builder()
                                                   .duration(60)
                                                   .createdAt(LocalDateTime.now())
                                                   .rulingId(mockedId)
                                                   .build();

        when(associateRepository.findById(mockedId)).thenReturn(Optional.of(associateEntity));
        when(sessionRepository.findById(mockedId)).thenReturn(Optional.of(sessionEntity));
        when(voteRepository.findByAssociateIdAndSessionId(mockedId, mockedId)).thenReturn(Optional.empty());
        when(voteRepository.save(voteEntity)).thenReturn(voteEntity);

        VoteResponseDTO voteResponseDTO = voteService.create(CreatedVoteDTO.builder()
                                                                           .associateId(mockedId)
                                                                           .sessionId(mockedId)
                                                                           .resultVote(true)
                                                                           .build());

        Assertions.assertNotNull(voteResponseDTO);
    }

    @Test
    @DisplayName("Should Create Throws FieldValidationException If Missing Fields")
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        Assertions.assertThrows(FieldValidationException.class, () -> voteService.create(CreatedVoteDTO.builder().build()));
    }

    @Test
    @DisplayName("Should Create Throws AssociateNotFoundException If Associate Not Found")
    void shouldCreateThrowsAssociateNotFoundExceptionIfAssociateNotFound() {
        Integer mockedAssociateId = 1;

        when(associateRepository.findById(mockedAssociateId)).thenReturn(Optional.empty());

        Assertions.assertThrows(AssociateNotFoundException.class, () -> voteService.create(CreatedVoteDTO.builder()
                                                                                                                         .associateId(mockedAssociateId)
                                                                                                                         .sessionId(mockedAssociateId)
                                                                                                                         .resultVote(true)
                                                                                                                         .build()));
    }

    @Test
    @DisplayName("Should Create Throws SessionNotFoundException If Session Not Found")
    void shouldCreateThrowsSessionNotFoundExceptionIfSessionNotFound() {
        Integer mockedAssociateId = 1;
        Integer mockedSessionId = 1;

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .name("Associate Test")
                                                         .cpf("661.338.900-53")
                                                         .build();

        when(associateRepository.findById(mockedAssociateId)).thenReturn(Optional.of(associateEntity));
        when(sessionRepository.findById(mockedSessionId)).thenReturn(Optional.empty());

        Assertions.assertThrows(SessionNotFoundException.class, () -> voteService.create(CreatedVoteDTO.builder()
                                                                                                                    .associateId(mockedSessionId)
                                                                                                                    .sessionId(mockedSessionId)
                                                                                                                    .resultVote(true)
                                                                                                                    .build()));
    }

    @Test
    @DisplayName("Should Create Throws SessionClosedException If Session is closed")
    void shouldCreateThrowsSessionClosedExceptionIfSessionIsClosed() {
        Integer mockedAssociateId = 1;
        Integer mockedSessionId = 1;
        Integer mockedRulingId = 1;

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .name("Associate Test")
                                                         .cpf("661.338.900-53")
                                                         .build();

        SessionEntity sessionEntity = SessionEntity.builder()
                                                   .duration(0)
                                                   .createdAt(LocalDateTime.now())
                                                   .rulingId(mockedRulingId)
                                                   .build();

        when(associateRepository.findById(mockedAssociateId)).thenReturn(Optional.of(associateEntity));
        when(sessionRepository.findById(mockedSessionId)).thenReturn(Optional.of(sessionEntity));

        Assertions.assertThrows(SessionClosedException.class, () -> voteService.create(CreatedVoteDTO.builder()
                                                                                                                  .associateId(mockedSessionId)
                                                                                                                  .sessionId(mockedSessionId)
                                                                                                                  .resultVote(true)
                                                                                                                  .build()));
    }
}
