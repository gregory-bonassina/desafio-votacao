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
public class RulingResponseDTO {

    private Integer id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
}
