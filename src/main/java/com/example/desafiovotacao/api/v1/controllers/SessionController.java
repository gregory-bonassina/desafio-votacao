package com.example.desafiovotacao.api.v1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.SessionControllerApi;
import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.SessionResponseDTO;
import com.example.desafiovotacao.services.ISessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SessionController implements SessionControllerApi {

    private final ISessionService sessionSerivice;

    @Override
    public ResponseEntity<Object> open(@Valid CreatedSessionDTO createdSessionDTO) {
        try {
            SessionResponseDTO result = this.sessionSerivice.create(createdSessionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> listAll() {
        try {
            List<SessionResponseDTO> result = this.sessionSerivice.listAllSessions();
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> findById(@PathVariable Integer sessionId) {
        try {
            SessionResponseDTO result = this.sessionSerivice.findById(sessionId);
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
