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
public class AssociateResponseDTO {
    private Integer id;
    private String name;
    private String cpf;
    private LocalDateTime createdAt;
}
