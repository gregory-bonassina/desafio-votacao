package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/vote")
@Tag(name = "Votos", description = "Informações dos Votos")
public interface VoteControllerApi {

    @PostMapping
        @Operation(
            summary = "Realiza o cadastro de um voto do associado",
            description = "Realiza o cadastro de um voto do associado em uma sessão ativa de determinada pauta"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreatedVoteDTO.class))
        })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedVoteDTO createdVoteDTO);
}
