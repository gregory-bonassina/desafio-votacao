package com.example.desafiovotacao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.SessionResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.exceptions.SessionNotFoundException;
import com.example.desafiovotacao.exceptions.SessionOpenException;
import com.example.desafiovotacao.repositories.RulingRepository;
import com.example.desafiovotacao.repositories.SessionRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionService implements ISessionService {
    
    private final SessionRepository sessionRepository;
    private final RulingRepository rulingRepository;

    @Override
    public SessionResponseDTO create(CreatedSessionDTO createdSessionDTO) {
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
        return sessionEntityToResponseDTO(newSessionEntity);
    }

    @Override
    public List<SessionResponseDTO> listAllSessions() {
        List<SessionEntity> allSessions = this.sessionRepository.findAll();

        return allSessions.stream().map(this::sessionEntityToResponseDTO).toList();
    }

    @Override
    public SessionResponseDTO findById(Integer sessionId) {
        SessionEntity sessionEntity = this.sessionRepository.findById(sessionId)
                                                            .orElseThrow(() -> {
                                                                throw new SessionNotFoundException();
                                                            });

        return sessionEntityToResponseDTO(sessionEntity);
    }

    private SessionResponseDTO sessionEntityToResponseDTO(SessionEntity sessionEntity) {
        return SessionResponseDTO.builder()
                                 .id(sessionEntity.getId())
                                 .rulingId(sessionEntity.getRulingId())
                                 .duration(sessionEntity.getDuration())
                                 .createdAt(sessionEntity.getCreatedAt())
                                 .build();
    }

    private void validateFields(CreatedSessionDTO createdSessionDTO) {
        if (createdSessionDTO.getRulingId() == null) {
            throw new FieldValidationException("rulingId");
        }
    }
}
