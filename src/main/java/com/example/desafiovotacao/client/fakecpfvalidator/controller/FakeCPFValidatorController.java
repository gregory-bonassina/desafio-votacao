package com.example.desafiovotacao.client.fakecpfvalidator.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.client.fakecpfvalidator.dto.FakeCPFDTO;
import com.example.desafiovotacao.client.fakecpfvalidator.service.FakeCPFValidatorService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
public class FakeCPFValidatorController {

    private final FakeCPFValidatorService fakeCPFValidatorService;
    
    @PostMapping("/cpf/validate/{cpf}")
    public ResponseEntity<FakeCPFDTO> validateCpf(@PathVariable String cpf){
        return fakeCPFValidatorService.isCpfValid(cpf);
    }
}
