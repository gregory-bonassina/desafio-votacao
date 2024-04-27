package com.example.desafiovotacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.entities.VoteEntity;

public interface VoteRepository extends JpaRepository<VoteEntity, Integer> {
 
    Optional<VoteEntity> findByAssociateIdAndSessionId(Integer associateId, Integer sessionId);
}
