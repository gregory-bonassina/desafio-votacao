package com.example.desafiovotacao.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.exceptions.AssociateFoundException;
import com.example.desafiovotacao.exceptions.AssociateNotFoundException;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.WrongCPFException;
import com.example.desafiovotacao.repositories.AssociateRepository;

@ExtendWith(MockitoExtension.class)
public class AssociateServiceTest {
    
    @InjectMocks
    private AssociateService associateService;

    @Mock
    private AssociateRepository associateRepository;

    @Test
    @DisplayName("Should Create Associate")
    void shouldCreateAssociate() {
        String mockedName = "Associate Test";
        String mockedCPF = "661.338.900-53";

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .name(mockedName) 
                                                         .cpf(mockedCPF)
                                                         .build();

        when(associateRepository.findByCpf(mockedCPF)).thenReturn(Optional.empty());
        when(associateRepository.save(associateEntity)).thenReturn(associateEntity);
        
        AssociateResponseDTO associateResponseDTO = associateService.create(CreatedAssociateDTO.builder()
                                                                                               .name(mockedName)
                                                                                               .cpf(mockedCPF)
                                                                                               .build());

        Assertions.assertNotNull(associateResponseDTO);
        Assertions.assertEquals(mockedName, associateResponseDTO.getName());
        Assertions.assertEquals(mockedCPF, associateResponseDTO.getCpf());
    }

    @Test
    @DisplayName("Should Create Throws FieldValidationException If Missing Fields")
    void shouldCreateThrowsFieldValidationExceptionIfMissingFields() {
        Assertions.assertThrows(FieldValidationException.class, () -> associateService.create(CreatedAssociateDTO.builder().build()));
    }

    @Test
    @DisplayName("Should Create Throws AssociateFoundException If Already Exists Associate With Same CPF")
    void shouldCreateThrowsAssociateFoundExceptionIfAlreadyExistsAssociateWithSameCPF() {
        String mockedName = "Associate Test";
        String mockedCPF = "661.338.900-53";

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .name(mockedName) 
                                                         .cpf(mockedCPF)
                                                         .build();

        when(associateRepository.findByCpf(mockedCPF)).thenReturn(Optional.of(associateEntity));

        Assertions.assertThrows(AssociateFoundException.class, () -> associateService.create(CreatedAssociateDTO.builder()
                                                                                                                             .name(mockedName)
                                                                                                                             .cpf(mockedCPF)
                                                                                                                             .build()));
    }

    @Test
    @DisplayName("Should Create Throws WrongCPFException If CPF Is Invalid")
    void shouldCreateThrowsWrongCPFExceptionIfCPFIsInvalid() {
        String mockedName = "Associate Test";
        String mockedCPF = "000.000.000-00";

        when(associateRepository.findByCpf(mockedCPF)).thenReturn(Optional.empty());
        
        Assertions.assertThrows(WrongCPFException.class, () -> associateService.create(CreatedAssociateDTO.builder()
                                                                                                                       .name(mockedName)
                                                                                                                       .cpf(mockedCPF)
                                                                                                                       .build()));
    }

    @Test
    @DisplayName("Should Find By Id")
    void shouldFindById() {
        Integer mockedId = 1;
        String mockedName = "Associate Test";
        String mockedCPF = "661.338.900-53";

        AssociateEntity associateEntity = AssociateEntity.builder()
                                                         .id(mockedId)
                                                         .cpf(mockedCPF)
                                                         .name(mockedName)
                                                         .build();

        when(associateRepository.findById(mockedId)).thenReturn(Optional.of(associateEntity));

        AssociateResponseDTO associateResponseDTO = associateService.findById(associateEntity.getId());

        Assertions.assertNotNull(associateResponseDTO);
        Assertions.assertEquals(mockedId, associateResponseDTO.getId());
        Assertions.assertEquals(mockedName, associateResponseDTO.getName());
        Assertions.assertEquals(mockedCPF, associateResponseDTO.getCpf());
    }

    @Test
    @DisplayName("Should Find By Id Throws AssociateNotFoundException If Associate NotFound")
    void shouldFindByIdThrowsAssociateNotFoundExceptionIfAssociateNotFound() {
        Integer mockedId = 1;

        when(associateRepository.findById(mockedId)).thenReturn(Optional.empty());

        Assertions.assertThrows(AssociateNotFoundException.class, () -> associateService.findById(mockedId));
    }
}
