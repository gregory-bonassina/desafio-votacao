package com.example.desafiovotacao.api.v1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.RulingControllerApi;
import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
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
            RulingResponseDTO result = this.rulingService.create(createdRulingDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> listAll() {
        try {
            List<RulingResponseDTO> result = this.rulingService.listAllRulings();
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> findById(Integer rulingId) {
        try {
            RulingResponseDTO result = this.rulingService.findById(rulingId);
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
}
