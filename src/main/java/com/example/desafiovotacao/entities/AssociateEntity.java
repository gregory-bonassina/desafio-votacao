package com.example.desafiovotacao.entities;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "associates")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AssociateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "O campo [name] é obrigatório")
    private String name;

    @NotBlank(message = "O campo [cpf] é obrigatório")
    @Column(unique = true)
    private String cpf;

    @CreationTimestamp
    private LocalDate createdAt;
}
