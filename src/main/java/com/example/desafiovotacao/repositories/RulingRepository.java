package com.example.desafiovotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.example.desafiovotacao.entities.RulingEntity;

@Repository
public interface RulingRepository extends JpaRepository<RulingEntity, Integer>{
 
    @Query(
        """
            SELECT NEW com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO( 
                SUM(CASE WHEN V.resultVote = TRUE THEN 1 ELSE 0 END),
                SUM(CASE WHEN V.resultVote = FALSE THEN 1 ELSE 0 END)
            ) 
            FROM RulingEntity R 
                JOIN SessionEntity S ON S.rulingId = R.id 
                JOIN VoteEntity V ON V.sessionId = S.id 
            WHERE 
                S.createdAt = (SELECT MAX(S2.createdAt) 
                                FROM SessionEntity S2 WHERE S2.rulingId = R.id) 
            AND 
                R.id = :rulingId
        """
    )
    VoteResultResponseDTO countVotes(Integer rulingId);
}
