package com.example.desafiovotacao.services;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.repositories.RulingRepository;

@ExtendWith(MockitoExtension.class)
public class RulingServiceTest {
    
    @InjectMocks
    private RulingService rulingService;

    @Mock
    private RulingRepository rulingRepository;

    @Test
    @DisplayName("Should Create Ruling")
    void shouldCreateRuling() {
        String mockTitle = "Ruling test";
        String mockDescription = "Description test";

        RulingEntity rulingEntity = RulingEntity.builder()
                                                .title(mockTitle)
                                                .description(mockDescription)
                                                .build();

        when(rulingRepository.save(rulingEntity)).thenReturn(rulingEntity);

        RulingResponseDTO rulingResponseDTO = rulingService.create(CreatedRulingDTO.builder()
                                                                                   .title(mockTitle)
                                                                                   .description(mockDescription)
                                                                                   .build());

        Assertions.assertNotNull(rulingResponseDTO);
        Assertions.assertEquals(mockTitle, rulingResponseDTO.getTitle());
        Assertions.assertEquals(mockDescription, rulingResponseDTO.getDescription());
    }

    @Test
    @DisplayName("Should Create Throws FieldValidationException If Missing Fields")
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        Assertions.assertThrows(FieldValidationException.class, () -> rulingService.create(CreatedRulingDTO.builder().build()));
    }

    @Test
    @DisplayName("Should Find By Id")
    void shouldFindById() {
        Integer mockedId = 1;
        String mockTitle = "Ruling test";
        String mockDescription = "Description test";

        RulingEntity rulingEntity = RulingEntity.builder()
                                                .id(mockedId)
                                                .title(mockTitle)
                                                .description(mockDescription)
                                                .build();

        when(rulingRepository.findById(mockedId)).thenReturn(Optional.of(rulingEntity));

        RulingResponseDTO rulingResponseDTO = rulingService.findById(rulingEntity.getId());

        Assertions.assertNotNull(rulingResponseDTO);
        Assertions.assertEquals(mockTitle, rulingResponseDTO.getTitle());
        Assertions.assertEquals(mockDescription, rulingResponseDTO.getDescription());
    }

    @Test
    @DisplayName("Should Find By Id Throws RulingNotFoundException If Ruling Not Found")
    void shouldFindByIdThrowsRulingNotFoundExceptionIfRulingNotFound() {
        Integer mockedId = 1;

        when(rulingRepository.findById(mockedId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RulingNotFoundException.class, () -> rulingService.findById(mockedId));
    }

    @Test
    @DisplayName("Should Count Votes")
    void shouldCountVotes() {
        Integer mockedId = 1;
        String mockTitle = "Ruling test";
        String mockDescription = "Description test";
        String mockResult = "Aprovada";

        RulingEntity rulingEntity = RulingEntity.builder()
                                                .id(mockedId)
                                                .title(mockTitle)
                                                .description(mockDescription)
                                                .build();

        when(rulingRepository.findById(mockedId)).thenReturn(Optional.of(rulingEntity));
        when(rulingRepository.countVotes(mockedId)).thenReturn(VoteResultResponseDTO.builder()
                                                                                    .title(mockTitle)
                                                                                    .description(mockDescription)
                                                                                    .favorVotes(1L)
                                                                                    .againstVotes(0L)
                                                                                    .result(mockResult)
                                                                                    .build());

        VoteResultResponseDTO voteResultResponseDTO = rulingService.countVotes(rulingEntity.getId());

        Assertions.assertNotNull(voteResultResponseDTO);
        Assertions.assertEquals(1, voteResultResponseDTO.getFavorVotes());
        Assertions.assertEquals(0, voteResultResponseDTO.getAgainstVotes());
        Assertions.assertEquals(mockResult, voteResultResponseDTO.getResult());
        Assertions.assertEquals(mockTitle, voteResultResponseDTO.getTitle());
        Assertions.assertEquals(mockDescription, voteResultResponseDTO.getDescription());
    }

    @Test
    @DisplayName("Should Count Votes Throw RulingNotFoundException If Ruling Not Found")
    void shouldCountVotesThrowRulingNotFoundExceptionIfRulingNotFound(){
        Integer mockedId = 1;

        when(rulingRepository.findById(mockedId)).thenReturn(Optional.empty());

        Assertions.assertThrows(RulingNotFoundException.class, () -> rulingService.countVotes(mockedId));
    }
}
