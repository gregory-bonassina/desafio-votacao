package com.example.desafiovotacao.exceptions;

public class FieldValidationException extends RuntimeException {

    public FieldValidationException( String field ) {
        super( "O campo '" + field + "' é obrigatório" );
    }
}
