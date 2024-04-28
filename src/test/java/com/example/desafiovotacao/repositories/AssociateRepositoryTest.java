package com.example.desafiovotacao.repositories;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.desafiovotacao.entities.AssociateEntity;

@DataJpaTest
public class AssociateRepositoryTest {

    @Autowired
    private AssociateRepository associateRepository;

    @Mock
    private AssociateEntity associateEntity;

    @BeforeEach
    void before() {
        associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                  .id(1)
                                                                  .cpf("661.338.900-53")
                                                                  .name("User for test")
                                                                  .build());
    }

    @AfterEach
    void after() {
        associateRepository.delete(associateEntity);
    }

    @Test
    void shouldFindById() {
        Optional<AssociateEntity> associateEntityById = associateRepository.findById(associateEntity.getId());

        Assertions.assertNotNull(associateEntityById);
    }

    @Test
    void shouldFindByCpf() {
        Optional<AssociateEntity> associateEntityById = associateRepository.findByCpf(associateEntity.getCpf());

        Assertions.assertNotNull(associateEntityById);
    }
}
