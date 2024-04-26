package com.example.desafiovotacao.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.entities.AssociateEntity;
import com.example.desafiovotacao.services.AssociateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/associate")
@RequiredArgsConstructor
public class AssociateController {
    
    private final AssociateService associateService;

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedAssociateDTO createdAssociateDTO) {
        try {
            AssociateEntity associateEntity = associateService.create(AssociateEntity.builder()
                                                                                     .cpf(createdAssociateDTO.getCpf())
                                                                                     .name(createdAssociateDTO.getName())
                                                                                     .build());
            return ResponseEntity.status(HttpStatus.CREATED).body(associateEntity);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
