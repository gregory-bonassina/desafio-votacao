package com.example.desafiovotacao.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.RulingControllerApi;
import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.entities.RulingEntity;
import com.example.desafiovotacao.services.RulingService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class RulingController implements RulingControllerApi {

    private final RulingService rulingService;

    @Override
    public ResponseEntity<Object> create(@Valid CreatedRulingDTO createdRulingDTO) {
        try {
            RulingEntity result = this.rulingService.create(createdRulingDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
