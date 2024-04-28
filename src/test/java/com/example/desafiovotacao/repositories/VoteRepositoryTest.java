package com.example.desafiovotacao.repositories;

import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.entities.VoteEntity;

@DataJpaTest
public class VoteRepositoryTest {
    
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AssociateRepository associateRepository;
    @Autowired
    private RulingRepository rulingRepository;
    @Autowired
    private SessionRepository sessionRepository;

    @Mock
    private VoteEntity voteEntity;
    @Mock
    private AssociateEntity associateEntity;
    @Mock
    private RulingEntity rulingEntity;
    @Mock
    private SessionEntity sessionEntity;

    @BeforeEach
    void before() {
        associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                  .name("Test Associate")
                                                                  .cpf("661.338.900-53")
                                                                  .build());

        rulingEntity = rulingRepository.save(RulingEntity.builder()
                                                         .title("Test Ruling")
                                                         .description("Test Description")
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
        Optional<VoteEntity> voteEntityById = voteRepository.findById(voteEntity.getId());

        Assertions.assertNotNull(voteEntityById);
    }

    @Test
    void shouldFindByAssociateIdAndSessionId() {
        Optional<VoteEntity> voteEntity = voteRepository.findByAssociateIdAndSessionId(associateEntity.getId(), sessionEntity.getId());

        Assertions.assertNotNull(voteEntity);
    }
}
