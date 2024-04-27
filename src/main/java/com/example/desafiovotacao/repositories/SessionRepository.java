package com.example.desafiovotacao.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.entities.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    
    List<SessionEntity> findAllByRulingId(Integer rulingId);
}
