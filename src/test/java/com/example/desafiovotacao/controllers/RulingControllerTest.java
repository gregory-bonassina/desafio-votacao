package com.example.desafiovotacao.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.List;

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

import com.example.desafiovotacao.dtos.CreatedRulingDTO;
import com.example.desafiovotacao.dtos.responses.RulingResponseDTO;
import com.example.desafiovotacao.dtos.responses.VoteResultResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Ruling Controller")
public class RulingControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreatedRulingDTO createdRulingDTO;
    @Mock
    private RulingResponseDTO rulingResponseDTO;
    @Mock
    private VoteResultResponseDTO voteResultDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String mockedTitle = "Ruling Test";
        String mockedDescription = "Description test";
        LocalDateTime mocekdCreatedAt = LocalDateTime.of(2024, 04, 28, 07, 00, 00);

        createdRulingDTO = CreatedRulingDTO.builder()
                                           .title(mockedTitle)
                                           .description(mockedDescription)
                                           .build();

        rulingResponseDTO = RulingResponseDTO.builder()
                                             .id(1)
                                             .title(mockedTitle)
                                             .description(mockedDescription)
                                             .createdAt(mocekdCreatedAt)
                                             .build();

        voteResultDTO = VoteResultResponseDTO.builder()
                                             .title(mockedTitle)
                                             .description(mockedDescription)
                                             .favorVotes(1L)
                                             .againstVotes(0L)
                                             .result("Aprovada")
                                             .build();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    @DisplayName("Should Create Ruling")
    void shouldCreateRuling() throws Exception {
        mockMvc.perform(post("/api/v1/ruling/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                    .content(objectMapper.writeValueAsString(createdRulingDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    @DisplayName("Should Not Create Ruling")
    void shouldNotCreateRuling() throws Exception {
        mockMvc.perform(post("/api/v1/ruling/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                    .content(objectMapper.writeValueAsString(CreatedRulingDTO.builder().build())))
               .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertRuling.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Find By Id") 
    void shouldFindById() throws Exception {
        mockMvc.perform(get("/api/v1/ruling/{rulingId}", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(rulingResponseDTO)));
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertRuling.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Find All")
    void shouldFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/ruling/", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(List.of(rulingResponseDTO))));
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertAssociate.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertRuling.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertSession.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertVote.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Count Votes")
    void shouldCountVotes() throws Exception {
        mockMvc.perform(get("/api/v1/ruling/count/votes/{rulingId}", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(voteResultDTO)));
    }
}
