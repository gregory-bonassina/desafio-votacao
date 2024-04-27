package com.example.desafiovotacao.services;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.exceptions.AssociateFoundException;
import com.example.desafiovotacao.exceptions.AssociateNotFoundException;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.WrongCPFException;
import com.example.desafiovotacao.repositories.AssociateRepository;
import com.example.desafiovotacao.utils.CPFUtilities;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AssociateService {
    
    private final AssociateRepository associateRepository;

    public AssociateEntity create(CreatedAssociateDTO createdAssociateDTO) {
        validateFields(createdAssociateDTO);

        this.associateRepository.findByCpf(createdAssociateDTO.getCpf()).ifPresent( user -> {
            throw new AssociateFoundException();
        });

        if ( ! CPFUtilities.validateCPF(createdAssociateDTO.getCpf()) ) {
            throw new WrongCPFException();
        }

        AssociateEntity newAssociateEntity = this.associateRepository.save(AssociateEntity.builder()
                                                                                          .cpf(createdAssociateDTO.getCpf())
                                                                                          .name(createdAssociateDTO.getName())
                                                                                          .build());

        return newAssociateEntity;
    }

    public AssociateEntity getAssociateByCpf(String cpf) {
        return this.associateRepository.findByCpf(cpf).orElseThrow(() -> {
            throw new AssociateNotFoundException();
        });
    }

    private void validateFields(CreatedAssociateDTO createdAssociateDTO) {
        if (createdAssociateDTO.getCpf() == null || createdAssociateDTO.getCpf().isEmpty()) {
            throw new FieldValidationException("cpf");
        }

        if (createdAssociateDTO.getName() == null || createdAssociateDTO.getName().isEmpty()) {
            throw new FieldValidationException("name");
        }
    }
}
