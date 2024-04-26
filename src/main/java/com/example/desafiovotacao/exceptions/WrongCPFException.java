package com.example.desafiovotacao.exceptions;

public class WrongCPFException extends RuntimeException {
    public WrongCPFException() {
        super("CPF não é valido");
    }
}
