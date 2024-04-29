package com.example.desafiovotacao.api.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedSessionDTO;
import com.example.desafiovotacao.dtos.responses.SessionResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/session")
@Tag(name = "Sessões", description = "Informações das Sessões")
public interface SessionControllerApi {
    
    @PostMapping("/open")
    @Operation(
            summary = "Realiza a abertura de uma nova sessão de votação",
            description = "Realiza a abertura de uma nova sessão de votação para determinada pauta"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreatedSessionDTO.class))
        })
    })
    public ResponseEntity<Object> open(@Valid @RequestBody CreatedSessionDTO createdSessionDTO);

    @GetMapping("/")
    @Operation(
            summary = "Listar todas as sessões",
            description = "Realiza a busca de todas as sessões cadastradas"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = List.class))
        }),
    })
    public ResponseEntity<Object> listAll();

    @GetMapping("/{sessionId}")
    @Operation(
            summary = "Buscar sessão por código identificador",
            description = "Realiza a busca de uma sessão pelo código identificador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = SessionResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Session not found")
    })
    public ResponseEntity<Object> findById(@PathVariable Integer sessionId);
}
