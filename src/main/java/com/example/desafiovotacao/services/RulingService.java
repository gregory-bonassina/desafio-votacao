package com.example.desafiovotacao.services;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.repositories.RulingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RulingService {
    
    private RulingRepository rulingRepository;

    public RulingEntity create(CreatedRulingDTO createdRulingDTO) {
        validateFields(createdRulingDTO);

        RulingEntity newRulingEntity = this.rulingRepository.save(RulingEntity.builder()
                                                            .title(createdRulingDTO.getTitle())
                                                            .description(createdRulingDTO.getDescription())
                                                            .build());

        return newRulingEntity;
    }

    private void validateFields(CreatedRulingDTO createdRulingDTO) {
        if (createdRulingDTO.getTitle() == null || createdRulingDTO.getTitle().isEmpty()) {
            throw new FieldValidationException("title");
        }
    }
}
