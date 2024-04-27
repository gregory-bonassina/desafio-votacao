package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/session")
public interface SessionControllerApi {
    
    @PostMapping("/create")
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedSessionDTO createdSessionDTO);
}
