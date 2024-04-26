package com.example.desafiovotacao.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Data
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreatedAssociateDTO {
    private String name;
    private String cpf;
}
