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

import com.example.desafiovotacao.dtos.CreatedAssociateDTO;
import com.example.desafiovotacao.dtos.responses.AssociateResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

@SpringBootTest
@AutoConfigureMockMvc
@DisplayName("Associate Controller")
public class AssociateControllerTest {
    
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private CreatedAssociateDTO createdAssociateDTO;
    @Mock
    private AssociateResponseDTO associateResponseDTO;

    private ObjectMapper objectMapper;

    @BeforeEach
    void before() {
        objectMapper = new ObjectMapper()
                            .registerModule(new JavaTimeModule())
                            .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        String mockedName = "Associate Test";
        String mockedCPF = "661.338.900-53";
        LocalDateTime mocekdCreatedAt = LocalDateTime.of(2024, 04, 28, 07, 00, 00);
        
        createdAssociateDTO = CreatedAssociateDTO.builder()
                                                 .name(mockedName)
                                                 .cpf(mockedCPF)
                                                 .build();

        associateResponseDTO = AssociateResponseDTO.builder()
                                                   .id(1)
                                                   .name(mockedName)
                                                   .cpf(mockedCPF)
                                                   .createdAt(mocekdCreatedAt)
                                                   .build();
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    @DisplayName("Should Create Associate")
    void shouldCreateAssociate() throws Exception {
        mockMvc.perform(post("/api/v1/associate/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                    .content(objectMapper.writeValueAsString(createdAssociateDTO)))
               .andExpect(status().isCreated())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    @DisplayName("Should Not Create Associate")
    void shouldNotCreateAssociate() throws Exception {
        createdAssociateDTO.setCpf("000.000.000-00");

        mockMvc.perform(post("/api/v1/associate/create").contentType(MediaType.APPLICATION_JSON_VALUE)
                                                                    .content(objectMapper.writeValueAsString(createdAssociateDTO)))
               .andExpect(status().isBadRequest());
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertAssociate.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Find By Id") 
    void shouldFindById() throws Exception {
        mockMvc.perform(get("/api/v1/associate/{associateId}", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(associateResponseDTO)));
    }

    @Test
    @SqlGroup({
        @Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = "/scripts/insertAssociate.sql"),
        @Sql(executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD, scripts = "/scripts/resetDB.sql")
    })
    @DisplayName("Should Find All")
    void shouldFindAll() throws Exception {
        mockMvc.perform(get("/api/v1/associate/", 1))
               .andExpect(status().isOk())
               .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
               .andExpect(content().json(objectMapper.writeValueAsString(List.of(associateResponseDTO))));
    }
}
