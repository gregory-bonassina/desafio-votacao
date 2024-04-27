package com.example.desafiovotacao.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.entities.RulingEntity;

public interface RulingRepository extends JpaRepository<RulingEntity, Integer>{
    
}
