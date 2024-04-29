package com.example.desafiovotacao.api.v1.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.AssociateControllerApi;
import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;
import com.example.desafiovotacao.services.IAssociateService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AssociateController implements AssociateControllerApi {
    
    private final IAssociateService associateService;

    @Override
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedAssociateDTO createdAssociateDTO) {
        try {
            AssociateResponseDTO result = associateService.create(createdAssociateDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> listAll() {
        try {
            List<AssociateResponseDTO> result = this.associateService.listAllAssociates();
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @Override
    public ResponseEntity<Object> findById(@PathVariable Integer associateId) {
        try {
            AssociateResponseDTO result = this.associateService.findById(associateId);
            
            return ResponseEntity.ok().body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
