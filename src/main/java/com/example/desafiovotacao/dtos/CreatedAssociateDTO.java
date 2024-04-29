package com.example.desafiovotacao.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedAssociateDTO {

    @Schema(example = "Gregory Bonassina", requiredMode = RequiredMode.REQUIRED, description = "Nome do associado")
    private String name;
    @Schema(example = "XXX.XXX.XXX-XX", requiredMode = RequiredMode.REQUIRED, description = "CPF do associado")
    private String cpf;
}
