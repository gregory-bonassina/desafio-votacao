package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;

import jakarta.validation.Valid;

@RequestMapping("/api/v1/vote")
public interface VoteControllerApi {

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedVoteDTO createdVoteDTO);
}
