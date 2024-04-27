package com.example.desafiovotacao.exceptions;

public class SessionOpenException extends RuntimeException {
    
    public SessionOpenException() {
        super("Já existe uma sessão aberta para esta pauta");
    }
}
