package com.example.desafiovotacao.services;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.CreatedSessionResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.exceptions.SessionOpenException;
import com.example.desafiovotacao.repositories.RulingRepository;
import com.example.desafiovotacao.repositories.SessionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionService {
    
    private final SessionRepository sessionRepository;
    private final RulingRepository rulingRepository;

    public CreatedSessionResponseDTO create(CreatedSessionDTO createdSessionDTO) {
        validateFields(createdSessionDTO);

        RulingEntity rulingEntity = rulingRepository.findById(createdSessionDTO.getRulingId()).orElseThrow(() -> {
            throw new RulingNotFoundException();
        });

        this.sessionRepository.findAllByRulingId(createdSessionDTO.getRulingId()).forEach( session -> {
            if(!session.isClosed()) {
                throw new SessionOpenException();
            }
        } );

        SessionEntity newSessionEntity = this.sessionRepository.save(SessionEntity.builder()
                                                                                  .rulingId(rulingEntity.getId())
                                                                                  .duration(createdSessionDTO.getDuration() == null ? SessionEntity.DEFAULT_DURATION : createdSessionDTO.getDuration() )
                                                                                  .build());
        return CreatedSessionResponseDTO.builder()
                                        .id(newSessionEntity.getId())
                                        .rulingId(newSessionEntity.getRulingId())
                                        .duration(newSessionEntity.getDuration())
                                        .createdAt(newSessionEntity.getCreatedAt())
                                        .build();
    }

    private void validateFields(CreatedSessionDTO createdSessionDTO) {
        if (createdSessionDTO.getRulingId() == null) {
            throw new FieldValidationException("rulingId");
        }
    }
}
