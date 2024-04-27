package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/associate")
public interface AssociateControllerApi {

    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedAssociateDTO createdAssociateDTO);
    
    @GetMapping("/")
    public ResponseEntity<Object> listAll();

    @GetMapping("/{associateId}")
    public ResponseEntity<Object> findById(@PathVariable Integer associateId);
}
