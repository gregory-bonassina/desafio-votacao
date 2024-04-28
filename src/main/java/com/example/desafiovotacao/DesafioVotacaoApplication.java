package com.example.desafiovotacao;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class DesafioVotacaoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DesafioVotacaoApplication.class, args);
	}
}
