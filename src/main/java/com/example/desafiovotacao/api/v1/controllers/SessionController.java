package com.example.desafiovotacao.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.SessionControllerApi;
import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.CreatedSessionResponseDTO;
import com.example.desafiovotacao.services.SessionService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SessionController implements SessionControllerApi {

    private final SessionService sessionSerivice;

    @Override
    public ResponseEntity<Object> create(@Valid CreatedSessionDTO createdSessionDTO) {
        try {
            CreatedSessionResponseDTO result = sessionSerivice.create(createdSessionDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
