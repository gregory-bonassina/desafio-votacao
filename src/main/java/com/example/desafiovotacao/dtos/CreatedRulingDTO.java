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
public class CreatedRulingDTO {
    
    @Schema(example = "Nova Pauta", requiredMode = RequiredMode.REQUIRED, description = "Título da pauta")
    private String title;
    @Schema(example = "Descrição da pauta", requiredMode = RequiredMode.NOT_REQUIRED, description = "Descrição da pauta")
    private String description;
}
