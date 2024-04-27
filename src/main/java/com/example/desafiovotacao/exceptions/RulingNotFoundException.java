package com.example.desafiovotacao.exceptions;

public class RulingNotFoundException extends RuntimeException {
    
    public RulingNotFoundException() {
        super("Pauta não encontrada");
    }
}
