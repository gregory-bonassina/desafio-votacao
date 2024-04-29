package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.desafiovotacao.dtos.CPFValidatorDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RequestMapping("/api/v1/cpf")
@Tag(name = "CPF", description = "Validar cpf consumindo uma facade/fake client")
public interface CPFValidatorControllerApi {
    
    @PostMapping("/validate/{cpf}")
    @Operation(
            summary = "Validar CPF",
            description = "Realiza a validação do cpf consumindo uma facade/fake client"
    )
    @ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = CPFValidatorDTO.class))
        })
    })
    public ResponseEntity<Object> validateCPF(@PathVariable String cpf);
}
