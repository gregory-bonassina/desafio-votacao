package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/session")
public interface SessionControllerApi {
    
    @PostMapping("/open")
    public ResponseEntity<Object> open(@Valid @RequestBody CreatedSessionDTO createdSessionDTO);

    @GetMapping("/")
    public ResponseEntity<Object> listAll();

    @GetMapping("/{sessionId}")
    public ResponseEntity<Object> findById(@PathVariable Integer sessionId);
}
