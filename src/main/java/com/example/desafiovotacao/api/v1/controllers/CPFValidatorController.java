package com.example.desafiovotacao.api.v1.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.desafiovotacao.api.v1.CPFValidatorControllerApi;
import com.example.desafiovotacao.dtos.CPFValidatorDTO;
import com.example.desafiovotacao.facade.FakeCPFValidator;

import feign.FeignException;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CPFValidatorController implements CPFValidatorControllerApi {
    
    private final FakeCPFValidator fakeCPFValidator;

    @Override
    public ResponseEntity<Object> validateCPF(String cpf) {
        try {
            CPFValidatorDTO result = this.fakeCPFValidator.getValidCPF(cpf);

            return ResponseEntity.ok().body(result);
        } catch (FeignException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.contentUTF8());
        }
    }
}
