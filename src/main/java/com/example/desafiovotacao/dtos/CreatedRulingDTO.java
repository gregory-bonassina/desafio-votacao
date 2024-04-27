package com.example.desafiovotacao.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedRulingDTO {
    
    private String title;
    private String description;
}
