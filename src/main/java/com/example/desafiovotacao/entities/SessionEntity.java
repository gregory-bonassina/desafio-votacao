package com.example.desafiovotacao.entities;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "sessions")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SessionEntity {

    public static final Integer DEFAULT_DURATION = 60;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer duration;

    @ManyToOne()
    @JoinColumn(name = "ruling_id", insertable = false, updatable = false)
    private RulingEntity rulingEntity;

    @Column(name = "ruling_id", nullable = false)
    private Integer rulingId;

    @CreationTimestamp
    private LocalDateTime createdAt;
}
