package com.example.desafiovotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.entities.SessionEntity;

public interface SessionRepository extends JpaRepository<SessionEntity, Integer> {
    
}
