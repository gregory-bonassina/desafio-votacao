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
public class CreatedVoteDTO {
    
    private Boolean resultVote;
    private Integer associateId;
    private Integer sessionId;
}
