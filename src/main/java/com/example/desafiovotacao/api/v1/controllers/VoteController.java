package com.example.desafiovotacao.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.VoteControllerApi;
import com.example.desafiovotacao.dtos.CreatedVoteDTO;
import com.example.desafiovotacao.dtos.responses.VoteResponseDTO;
import com.example.desafiovotacao.services.IVoteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class VoteController implements VoteControllerApi {

    private final IVoteService voteService;

    @Override
    public ResponseEntity<Object> create(@Valid CreatedVoteDTO createdVoteDTO) {
        try {
            VoteResponseDTO result = this.voteService.create(createdVoteDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
