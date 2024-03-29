package edu.java.exception;

public class ClientException extends RuntimeException {
    private final int statusCode;

    public ClientException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }
}
