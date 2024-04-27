package com.example.desafiovotacao.exceptions;

public class SessionNotFoundException extends RuntimeException {
    
    public SessionNotFoundException() {
        super("Sessão não encontrada");
    }
}
