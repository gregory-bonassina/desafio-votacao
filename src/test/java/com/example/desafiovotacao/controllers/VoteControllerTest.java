package com.example.desafiovotacao.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlGroup;
import org.springframework.test.web.servlet.MockMvc;

import com.example.desafiovotacao.dtos.CreatedVoteDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Vote Controller")
public class VoteControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreatedVoteDTO createdVoteDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        Integer mockedAssociateId = 1;
        Integer mockedSessionId = 1;

        createdVoteDTO = CreatedVoteDTO.builder()
                                       .associateId(mockedAssociateId)
                                       .sessionId(mockedSessionId)
                                       .resultVote(true)
                                       .build();
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertAssociate.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertRuling.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertCurrentSession.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Create Vote")
    void shouldCreateVote() throws Exception {
        mockMvc.perform(post("/api/v1/vote").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                .content(objectMapper.writeValueAsString(createdVoteDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertAssociate.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertRuling.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertSession.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Not Create Vote If Session Is Closed")
    void shouldNotCreateVoteIfSessionIsClosed() throws Exception {
        mockMvc.perform(post("/api/v1/vote").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                .content(objectMapper.writeValueAsString(createdVoteDTO)))
               .andExpect(status().isBadRequest());
    }
}
