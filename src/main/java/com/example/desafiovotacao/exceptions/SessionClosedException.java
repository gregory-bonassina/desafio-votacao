package com.example.desafiovotacao.exceptions;

public class SessionClosedException extends RuntimeException {
    
    public SessionClosedException() {
        super("Sessão já encerrada");
    }
}
