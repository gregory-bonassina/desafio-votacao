package com.example.desafiovotacao.services;

import java.util.List;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;

public interface IRulingService {
    
    public RulingResponseDTO create(CreatedRulingDTO createdRulingDTO);

    public RulingResponseDTO findById(Integer rulingId);

    public List<RulingResponseDTO> listAllRulings();

    public VoteResultResponseDTO countVotes(Integer rulingId);
}
