package com.example.desafiovotacao.repositories;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;

@DataJpaTest
public class SessionRepositoryTest {
    
    @Autowired
    private SessionRepository sessionRepository;
    @Autowired
    private RulingRepository rulingRepository;

    @Mock
    private SessionEntity sessionEntity;
    @Mock
    private RulingEntity rulingEntity;

    @BeforeEach
    void before() {
        rulingEntity = rulingRepository.save(RulingEntity.builder()
                                                         .title("Test Ruling")
                                                         .description("Test Description")
                                                         .build());

        sessionEntity = sessionRepository.save(SessionEntity.builder()
                                                            .rulingId(rulingEntity.getId())
                                                            .duration(60)
                                                            .build());
    }

    @Test
    void shouldFindById() {
        Optional<SessionEntity> sessionEntityById = sessionRepository.findById(sessionEntity.getId());

        Assertions.assertNotNull(sessionEntityById);
    }

    @Test
    void shoudFindAllByRulingId() {
        List<SessionEntity> allSessionsByRulingId = sessionRepository.findAllByRulingId(rulingEntity.getId());

        Assertions.assertEquals(allSessionsByRulingId.size(), 1);
    }
}
