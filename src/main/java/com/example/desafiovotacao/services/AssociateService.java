package com.example.desafiovotacao.services;

import org.springframework.stereotype.Service;

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

    public AssociateEntity create(AssociateEntity associateEntity) {
        validateFields(associateEntity);

        this.associateRepository.findByCpf(associateEntity.getCpf()).ifPresent( user -> {
            throw new AssociateFoundException();
        });

        if ( ! CPFUtilities.validateCPF(associateEntity.getCpf()) ) {
            throw new WrongCPFException();
        }

        return this.associateRepository.save(associateEntity);
    }

    public AssociateEntity getAssociateByCpf(String cpf) {
        return this.associateRepository.findByCpf(cpf).orElseThrow(() -> {
            throw new AssociateNotFoundException();
        });
    }

    private void validateFields(AssociateEntity associateEntity) {
        if (associateEntity.getCpf() == null || associateEntity.getCpf().isEmpty() ) {
            throw new FieldValidationException("cpf");
        }

        if (associateEntity.getName() == null || associateEntity.getName().isEmpty() ) {
            throw new FieldValidationException("name");
        }
    }
}
