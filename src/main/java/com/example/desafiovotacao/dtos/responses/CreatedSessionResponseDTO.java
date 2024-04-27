package com.example.desafiovotacao.dtos.responses;

import java.time.LocalDateTime;

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
public class CreatedSessionResponseDTO {

    private Integer id;
    private Integer rulingId;
    private Integer duration;
    private LocalDateTime createdAt;
}
