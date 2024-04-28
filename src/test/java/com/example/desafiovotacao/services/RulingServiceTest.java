package com.example.desafiovotacao.services;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.entities.VoteEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.repositories.AssociateRepository;
import com.example.desafiovotacao.repositories.RulingRepository;
import com.example.desafiovotacao.repositories.SessionRepository;
import com.example.desafiovotacao.repositories.VoteRepository;

@SpringBootTest
@AutoConfigureTestDatabase
public class RulingServiceTest {
    
    @Autowired
    private RulingService rulingService;

    @Autowired
    private RulingRepository rulingRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Mock
    private CreatedRulingDTO createdRulingDTO;

    @BeforeEach
    void before() {
        createdRulingDTO = CreatedRulingDTO.builder()
                                           .title("Ruling Test")
                                           .description("Description test")
                                           .build();
    }

    @AfterEach
    void after() {
        voteRepository.deleteAll();
        sessionRepository.deleteAll();
        rulingRepository.deleteAll();
        associateRepository.deleteAll();
    }

    @Test
    void shouldCreateRuling() {
        RulingResponseDTO rulingResponseDTO = rulingService.create(createdRulingDTO);

        Assertions.assertNotNull(rulingResponseDTO);
    }

    @Test
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        try {
            rulingService.create(CreatedRulingDTO.builder().build());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FieldValidationException.class);
        }
    }

    @Test
    void shouldFindById() {
        RulingEntity rulingEntity = rulingRepository.save(RulingEntity.builder()
                                                                      .title("Ruling Test")
                                                                      .description("Description test")
                                                                      .build());

        RulingResponseDTO associateResponseDTO = rulingService.findById(rulingEntity.getId());

        Assertions.assertNotNull(associateResponseDTO);
    }

    @Test
    void shouldFindByIdThrowsRulingNotFoundExceptionIfAssociateNotFound() {
        try {
            rulingService.findById(2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RulingNotFoundException.class);
        }
    }

    @Test
    void shouldCountVotes() {
        AssociateEntity associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                                  .cpf("661.338.900-53")
                                                                                  .name("Associate Test")
                                                                                  .build());

        RulingEntity rulingEntity = rulingRepository.save(RulingEntity.builder()
                                                                      .title("Ruling Test")
                                                                      .description("Description test")
                                                                      .build());

        SessionEntity sessionEntity = sessionRepository.save(SessionEntity.builder()
                                                                          .rulingId(rulingEntity.getId())
                                                                          .duration(60)
                                                                          .build());

        voteRepository.save(VoteEntity.builder()
                                      .associateId(associateEntity.getId())
                                      .sessionId(sessionEntity.getId())
                                      .resultVote(true)
                                      .build());

        VoteResultResponseDTO voteResultResponseDTO = rulingService.countVotes(rulingEntity.getId());

        Assertions.assertNotNull(voteResultResponseDTO);
        Assertions.assertEquals(1, voteResultResponseDTO.getFavorVotes());
        Assertions.assertEquals(0, voteResultResponseDTO.getAgainstVotes());
        Assertions.assertEquals("Aprovada", voteResultResponseDTO.getResult());
        Assertions.assertEquals(voteResultResponseDTO.getTitle(), rulingEntity.getTitle());
        Assertions.assertEquals(voteResultResponseDTO.getDescription(), rulingEntity.getDescription());
    }
}
