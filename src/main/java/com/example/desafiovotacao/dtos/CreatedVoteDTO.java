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
public class CreatedVoteDTO {
    
    @Schema(example = "true", requiredMode = RequiredMode.REQUIRED, description = "Resultado do voto do associado true=Sim, false=não")
    private Boolean resultVote;
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "ID do associado")
    private Integer associateId;
    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "ID da sessão")
    private Integer sessionId;
}
