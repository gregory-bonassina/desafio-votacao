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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "votes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Boolean resultVote;

    @ManyToOne()
    @JoinColumn(name = "associate_id", insertable = false, updatable = false)
    private AssociateEntity associateEntity;

    @Column(name = "associate_id", nullable = false)
    private Integer associateId;

    @ManyToOne()
    @JoinColumn(name = "session_id", insertable = false, updatable = false)
    private SessionEntity sessionEntity;

    @Column(name = "session_id", nullable = false)
    private Integer sessionId;

    @CreationTimestamp
    private LocalDateTime createdAt;
    
}
