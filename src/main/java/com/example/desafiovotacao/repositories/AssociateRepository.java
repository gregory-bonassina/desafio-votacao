package com.example.desafiovotacao.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.desafiovotacao.entities.AssociateEntity;

public interface AssociateRepository extends JpaRepository<AssociateEntity, Integer> {
    Optional<AssociateEntity> findByCpf(String cpf);
}
