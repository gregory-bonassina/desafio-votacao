package com.example.desafiovotacao.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.exceptions.FieldValidationException;
import com.example.desafiovotacao.exceptions.RulingNotFoundException;
import com.example.desafiovotacao.repositories.RulingRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RulingService {
    
    private RulingRepository rulingRepository;

    public RulingResponseDTO create(CreatedRulingDTO createdRulingDTO) {
        validateFields(createdRulingDTO);

        RulingEntity newRulingEntity = this.rulingRepository.save(RulingEntity.builder()
                                                            .title(createdRulingDTO.getTitle())
                                                            .description(createdRulingDTO.getDescription())
                                                            .build());

        return rulingEntityToResponseDTO(newRulingEntity);
    }

    public List<RulingResponseDTO> listAllRulings() {
        List<RulingEntity> allRulings = this.rulingRepository.findAll();

        return allRulings.stream().map(this::rulingEntityToResponseDTO).toList();
    }

    public RulingResponseDTO findById(Integer rulingId) {
        RulingEntity rulingEntity = this.rulingRepository.findById(rulingId)
                                                         .orElseThrow(() -> {
                                                            throw new RulingNotFoundException();
                                                         });

        return rulingEntityToResponseDTO(rulingEntity);
    }

    public VoteResultResponseDTO countVotes(Integer rulingId) {
        RulingEntity rulingEntity = this.rulingRepository.findById(rulingId)
                                                         .orElseThrow(() -> {
                                                            throw new RulingNotFoundException();
                                                         });

        VoteResultResponseDTO voteResultResponseDTO = this.rulingRepository.countVotes(rulingId);

        Long favorVotes = voteResultResponseDTO.getFavorVotes() != null ? voteResultResponseDTO.getFavorVotes() : 0;
        Long againstVotes = voteResultResponseDTO.getAgainstVotes() != null ? voteResultResponseDTO.getAgainstVotes() : 0;

        return VoteResultResponseDTO.builder()
                                    .favorVotes(favorVotes)
                                    .againstVotes(againstVotes)
                                    .title(rulingEntity.getTitle())
                                    .description(rulingEntity.getDescription())
                                    .result(favorVotes == againstVotes ? "Empate" : (favorVotes > againstVotes ? "Aprovada" : "Desaprovada"))
                                    .build();
    }

    private RulingResponseDTO rulingEntityToResponseDTO(RulingEntity sessionEntity) {
        return RulingResponseDTO.builder()
                                 .id(sessionEntity.getId())
                                 .title(sessionEntity.getTitle())
                                 .description(sessionEntity.getDescription())
                                 .createdAt(sessionEntity.getCreatedAt())
                                 .build();
    }

    private void validateFields(CreatedRulingDTO createdRulingDTO) {
        if (createdRulingDTO.getTitle() == null || createdRulingDTO.getTitle().isEmpty()) {
            throw new FieldValidationException("title");
        }
    }
}
