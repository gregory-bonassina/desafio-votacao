package com.example.desafiovotacao.services;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.exceptions.AssociateFoundException;
import com.example.desafiovotacao.exceptions.AssociateNotFoundException;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.WrongCPFException;
import com.example.desafiovotacao.repositories.AssociateRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
public class AssociateServiceTest {
    
    @Autowired
    private AssociateService associateService;

    @Autowired
    private AssociateRepository associateRepository;

    @Mock
    private CreatedAssociateDTO createdAssociateDTO;

    @BeforeEach
    void before() {
        createdAssociateDTO = CreatedAssociateDTO.builder()
                                                 .name("Associate test")
                                                 .cpf("661.338.900-53")
                                                 .build();
    }

    @AfterEach
    void after() {
        associateRepository.deleteAll();
    }

    @Test
    void shouldCreateAssociate() {
        AssociateResponseDTO associateResponseDTO = associateService.create(createdAssociateDTO);

        Assertions.assertNotNull(associateResponseDTO);
    }

    @Test
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        try {
            associateService.create(CreatedAssociateDTO.builder().build());
        } catch (Exception e) {
            assertThat(e).isInstanceOf(FieldValidationException.class);
        }
    }

    @Test
    void shouldCreateThrowsAssociateFoundExceptionIfAlreadyExistsAssociateWithSameCPF() {
        associateService.create(createdAssociateDTO);
        try {
            associateService.create(createdAssociateDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AssociateFoundException.class);
        }
    }

    @Test
    void shouldCreateThrowsWrongCPFExceptionIfCPFIsInvalid() {
        try {
            createdAssociateDTO.setCpf("000.000.000-00");
            associateService.create(createdAssociateDTO);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(WrongCPFException.class);
        }
    }

    @Test
    void shouldFindById() {
        AssociateEntity associateEntity = associateRepository.save(AssociateEntity.builder()
                                                                                  .cpf("661.338.900-53")
                                                                                  .name("Associate Test")
                                                                                  .build());

        AssociateResponseDTO associateResponseDTO = associateService.findById(associateEntity.getId());

        Assertions.assertNotNull(associateResponseDTO);
    }

    @Test
    void shouldFindByIdThrowsAssociateNotFoundExceptionIfAssociateNotFound() {
        try {
            associateService.findById(2);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(AssociateNotFoundException.class);
        }
    }
}
