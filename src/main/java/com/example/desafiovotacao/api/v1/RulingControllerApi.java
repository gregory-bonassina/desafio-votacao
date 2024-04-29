package com.example.desafiovotacao.api.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/ruling")
@Tag(name = "Pautas", description = "Informações das Pautas")
public interface RulingControllerApi {

    @PostMapping("/create")
    @Operation(
            summary = "Cadastrar uma nova pauta",
            description = "Realiza o cadastro de uma nova pauta para votação"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreatedRulingDTO.class))
        })
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedRulingDTO createdRulingDTO);

    @GetMapping("/")
    @Operation(
            summary = "Listar todas as pautas",
            description = "Realiza a busca de todas as pautas cadastradas"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = List.class))
        }),
    })
    public ResponseEntity<Object> listAll();

    @GetMapping("/{rulingId}")
    @Operation(
            summary = "Buscar pauta por código identificador",
            description = "Realiza a busca de uma pauta pelo código identificador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = RulingResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Ruling not found")
    })
    public ResponseEntity<Object> findById(@PathVariable Integer rulingId);

    @GetMapping("/count/votes/{rulingId}")
    @Operation(
            summary = "Realiza a contagem dos votos por código identificador de uma pauta",
            description = "Realiza a contagem dos votos com base na última sessão que occorreu para uma pauta"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = VoteResultResponseDTO.class))
        }),
    })
    public ResponseEntity<Object> countVotes(@PathVariable Integer rulingId);

} 
