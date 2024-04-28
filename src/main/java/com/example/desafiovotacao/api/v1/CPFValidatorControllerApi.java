package com.example.desafiovotacao.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/api/v1/cpf")
public interface CPFValidatorControllerApi {
    
    @PostMapping("/validate/{cpf}")
    public ResponseEntity<Object> validateCPF(@PathVariable String cpf);
}
