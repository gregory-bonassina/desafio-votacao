package com.example.desafiovotacao.dtos;

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
    
    private Integer rulingId;
    private Integer duration;
}
