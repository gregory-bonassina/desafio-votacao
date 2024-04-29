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
public class CreatedSessionDTO {

    @Schema(example = "1", requiredMode = RequiredMode.REQUIRED, description = "ID da pauta")
    private Integer rulingId;
    @Schema(example = "60", requiredMode = RequiredMode.NOT_REQUIRED, defaultValue = "60", description = "Duração de uma sessão em segundos")
    private Integer duration;
}
