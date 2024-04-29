package com.example.desafiovotacao.services;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;
import com.example.desafiovotacao.dtos.responses.VoteResponseDTO;

public interface IVoteService {
    
    public VoteResponseDTO create(CreatedVoteDTO createdVoteDTO);
}
