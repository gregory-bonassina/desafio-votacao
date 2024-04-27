package com.example.desafiovotacao.dtos.responses;

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
public class VoteResultResponseDTO {

    private String title;
    private String description;
    private String result;

    private Long favorVotes;
    private Long againstVotes;

    public VoteResultResponseDTO(Long favorVotes, Long againstVotes) {
        this.favorVotes = favorVotes;
        this.againstVotes = againstVotes;
    }
}
