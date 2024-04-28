package com.example.desafiovotacao.facade;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.desafiovotacao.dtos.CPFValidatorDTO;

@FeignClient(name = "fakeCPFValidator", url = "${fakecpfvalidator.url}")
public interface FakeCPFValidator {
    
    @PostMapping("/client/cpf/validate/{cpf}")
    CPFValidatorDTO getValidCPF(@PathVariable String cpf);
}
