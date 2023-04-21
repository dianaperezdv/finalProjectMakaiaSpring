package com.example.finalProject.Exception;

public class InvalidStatementException extends RuntimeException {

    public InvalidStatementException(String messageError) {
        super(messageError);
    }

    public InvalidStatementException(String messageError, Throwable cause) {
        super(messageError, cause);
    }
}
