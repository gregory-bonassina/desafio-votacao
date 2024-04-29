package com.example.desafiovotacao.services;

import java.util.List;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.SessionResponseDTO;

public interface ISessionService {
    
    public SessionResponseDTO create(CreatedSessionDTO createdSessionDTO);

    public List<SessionResponseDTO> listAllSessions();

    public SessionResponseDTO findById(Integer sessionId);
}
