package com.example.desafiovotacao.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.entities.VoteEntity;

@DataJpaTest
public class RulingRepositoryTest {
    
    @Autowired
    private RulingRepository rulingRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private VoteRepository voteRepository;

    @Mock
    private RulingEntity rulingEntity;
    @Mock
    private AssociateEntity associateEntity;
    @Mock
    private SessionEntity sessionEntity;
    @Mock
    private VoteEntity voteEntity;

    @BeforeEach
    void before() {
        rulingEntity = rulingRepository.save(RulingEntity.builder()
                                                         .title("Test Ruling")
                                                         .description("Test Description")
                                                         .build());

        associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                  .name("Test Associate")
                                                                  .cpf("661.338.900-53")
                                                                  .build());

        sessionEntity = sessionRepository.save(SessionEntity.builder()
                                                            .rulingId(rulingEntity.getId())
                                                            .duration(60)
                                                            .build());

        voteEntity = voteRepository.save(VoteEntity.builder()
                                                   .associateId(associateEntity.getId())
                                                   .sessionId(sessionEntity.getId())
                                                   .resultVote(true)
                                                   .build());
        
    }

    @Test
    void shouldFindById() {
        Optional<RulingEntity> rulingEntityById = rulingRepository.findById(rulingEntity.getId());

        Assertions.assertNotNull(rulingEntityById);
    }

    @Test
    void shouldCountVotes() {
        VoteResultResponseDTO voteResultResponseDTO = rulingRepository.countVotes(rulingEntity.getId());

        Assertions.assertEquals(voteResultResponseDTO, VoteResultResponseDTO.builder()
                                                                            .favorVotes(1L)
                                                                            .againstVotes(0L)
                                                                            .build());
    }
}
