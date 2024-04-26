package com.example.desafiovotacao.exceptions;

public class AssociateFoundException extends RuntimeException {
    public AssociateFoundException() {
        super("Associado já cadastrado");
    }
}
