package com.example.desafiovotacao.api.v1;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RequestMapping("/api/v1/associate")
@Tag(name = "Associados", description = "Informações dos Associados")
public interface AssociateControllerApi {

    @PostMapping("/create")
    @Operation(
            summary = "Cadastrar um novo associado",
            description = "Realiza o cadastro de um novo associado caso não exista"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CreatedAssociateDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Associate already exists")
    })
    public ResponseEntity<Object> create(@Valid @RequestBody CreatedAssociateDTO createdAssociateDTO);
    
    @GetMapping("/")
    @Operation(
            summary = "Listar todos os associados",
            description = "Realiza a busca de todos associados cadastrados"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = List.class))
        }),
    })
    public ResponseEntity<Object> listAll();

    @GetMapping("/{associateId}")
    @Operation(
            summary = "Buscar associado por código identificador",
            description = "Realiza a busca de um associado pelo código identificador"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = AssociateResponseDTO.class))
        }),
        @ApiResponse(responseCode = "400", description = "Associate not found")
    })
    public ResponseEntity<Object> findById(@PathVariable Integer associateId);
}
