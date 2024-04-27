package com.example.desafiovotacao.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;
import com.example.desafiovotacao.dtos.responses.CreatedVoteResponseDTO;
import com.example.desafiovotacao.entities.SessionEntity;
import com.example.desafiovotacao.entities.VoteEntity;
import com.example.desafiovotacao.exceptions.AssociateAlreadyVoteException;
import com.example.desafiovotacao.exceptions.AssociateNotFoundException;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.SessionClosedException;
import com.example.desafiovotacao.exceptions.SessionNotFoundException;
import com.example.desafiovotacao.repositories.AssociateRepository;
import com.example.desafiovotacao.repositories.SessionRepository;
import com.example.desafiovotacao.repositories.VoteRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final AssociateRepository associateRepository;
    private final SessionRepository sessionRepository;
    
    public CreatedVoteResponseDTO create(CreatedVoteDTO createdVoteDTO) {
        validateFields(createdVoteDTO);

        this.associateRepository.findById(createdVoteDTO.getAssociateId()).orElseThrow(() -> {
            throw new AssociateNotFoundException();
        });

        SessionEntity sessionEntity = this.sessionRepository.findById(createdVoteDTO.getSessionId()).orElseThrow(() -> {
            throw new SessionNotFoundException();
        });

        if(isSessionClosed(sessionEntity)) {
            throw new SessionClosedException();
        }

        this.voteRepository.findByAssociateIdAndSessionId(createdVoteDTO.getAssociateId(), createdVoteDTO.getSessionId()).ifPresent( vote -> {
            throw new AssociateAlreadyVoteException();
        });

        VoteEntity newVoteEntity = this.voteRepository.save(VoteEntity.builder()
                                                                      .associateId(createdVoteDTO.getAssociateId())
                                                                      .sessionId(createdVoteDTO.getSessionId())
                                                                      .resultVote(createdVoteDTO.getResultVote())
                                                                      .build());

        return CreatedVoteResponseDTO.builder()
                                     .id(newVoteEntity.getId())
                                     .associateId(newVoteEntity.getAssociateId())
                                     .sessionId(newVoteEntity.getSessionId())
                                     .resultVote(newVoteEntity.getResultVote() ? "Sim" : "Não")
                                     .createdAt(newVoteEntity.getCreatedAt())
                                     .build();
    }

    private boolean isSessionClosed(SessionEntity sessionEntity) {
        LocalDateTime now = LocalDateTime.now();

        LocalDateTime sessionEnd = sessionEntity.getCreatedAt().plusSeconds(sessionEntity.getDuration());
        
        return now.isAfter(sessionEnd);
    }

    private void validateFields(CreatedVoteDTO createdVoteDTO) {
        if (createdVoteDTO.getAssociateId() == null) {
            throw new FieldValidationException("associateId");
        }

        if (createdVoteDTO.getSessionId() == null) {
            throw new FieldValidationException("sessionId");
        }

        if (createdVoteDTO.getResultVote() == null) {
            throw new FieldValidationException("resultVote");
        }
    }
}
